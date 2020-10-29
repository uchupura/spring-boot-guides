# 스프링 배치

---
## 배치 설정
### 특정 배치만 실행 시키는 설정
* application.yml에 아래 코드 추가
* Program arguments로 job.name 값이 넘어오면 해당 값과 일치하는 Job만 실행
    * job.name이 없을 경우 NONE이 할당되며 이 경우 어떤 배치도 실행되지 않음
```shell script
spring.batch.job.names: ${job.name:NONE}
```
* 운영 환경에서는 아래와 같이 실행
```shell script
$ java -jar -Dspring.profiles.active=stage spring-boot-batch.jar --job.name=CustomDbChunkWriterJob version=4 status=JOIN
```

### BatchStatus vs ExitStatus
* BatchStatus : Job 또는 Step의 실행 결과
* ExitStatus : Step의 실행 후 상태
---
## 메타 테이블
### BATCH_JOB_INSTANCE
* 스프링 배치 실행 시 외부에서 받는 파라미터(Job Parameter)에 따라 생성되는 테이블
    * 예시) 특정 날짜를 Job Parameter로 넘기면 스프링 배치에서는 해당 날짜 데이터로 조회/가공/입력 작업 진행
* (중요) 같은 Batch Job이라도 Job Parameter가 다르면 BATCH_JOB_INSTANCE 테이블에 기록되며, Job Parameter가 같다면 테이블에 기록되지 않음
    * Job Parameter가 같아서 예외가 발생되면 BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION 테이블에 기록되지 않는다.
```shell script
Caused by: org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException: A job instance already exists and is complete for parameters={requestDate=20180805}.  If you want to run this job again, change the parameters.
```

### BATCH_JOB_EXECUTION
* BATCH_JOB_INSTANCE와 부모-자식 관계
* BATCH_JOB_EXECUTION은 자신의 부모 BATCH_JOB_EXECUTION이 성공/실패했던 히스토리 저장
* 배치가 실패 후 다시 실행되면 BATCH_JOB_EXECUTION 테이블에는 ROW가 추가되지만, BATCH_JOB_INSTANCE 테이블 입장에서는 Job Parameter가 변동이 없기 때문에 ROW가 추가되지 않음

---
## 배치 처리 흐름 제어
### Next
* 기능?
    * 순차적으로 Step들을 연결시킬 때 사용

### start(), on(), to(), from(), end()
* start()
    * 배치 스텝 시작 역활
* on()
    * 캐치 할 ExitStatus 지정
    * *일 경우 모든 ExitStatus 지정
* to()
    * 다음으로 이동할 Step 지정
* from()
    * 일종의 이벤트 리스너 역할
    * 상태값을 보고 일치하는 상태라면 to()에 포함된 step을 호출합니다.
    * step1의 이벤트 캐치가 FAILED로 되있는 상태에서 추가로 이벤트 캐치하려면 from을 써야만 함
* end()
    * end는 FlowBuilder를 반환하는 end와 FlowBuilder를 종료하는 end 2개가 있음
        * on("*")뒤에 있는 end는 FlowBuilder를 반환하는 end
        * build() 앞에 있는 end는 FlowBuilder를 종료하는 end
    * FlowBuilder를 반환하는 end 사용시 계속해서 from을 이어갈 수 있음


## Job Parameter
* 외부 혹은 내부에서 파라미터를 받아 여러 Batch 컴포넌트에서 사용할 수 있게 지원하는데 이 파라미터를 Job Parameter라고 한다.
* Job Parameter를 사용하기 위해서는 항상 Spring batch 전용 Scope를 선언해야한다.
    * @JobScope
        * Step 선언문에서 사용 가능
        * Job의 실행 시점에 해당 컴포넌트를 스프링 빈으로 등록 
    * @StepScope
        * Tasklet이나 ItemReader, ItemWriter, ItemProcessor에서 사용 가능
        * Step의 실행 시점에 해당 컴포넌트를 스프링 빈으로 등록
```java
@Value("#{jobParameters[파라미터명]}")
```

## Scope
* 스프링 빈의 기본 Scope는 Singleton으로 어플리케니션 구동 시점에 생성된다.
* @StepScope나 @JobScope를 사용하면 빈의 생성 시점을 지정된 Scope가 실행되는 시점으로 지연시킬 수 있다.
* Scope를 지연시킴으로 얻는 이점
    * JobParameter의 Late Binding이 가능
    * 동일한 컴포넌트를 병렬 혹은 동시에 사용할 때 유용
        * @StepScope 없이 Step을 병렬로 실행시키게 되면 서로 다른 Step에서 하나의 Tasklet 상태를 침범 할 수 있다.
        * @StepScope가 있다면 각각의 Step에서 별도의 Tasklet을 생성하고 관리하기 때문에 서로의 상태를 침범할 일이 없다.
        
## JobParameter vs 시스템 변수
* 시스템 변수를 사용할 경우 Spring Batch의 JobParameter 관련 기능을 사용할 수 없다.
    * (중요) 동일한 JobParameter로 같은 Job을 두 번 실행하지 않는다.
    * Parameter 관련 메타 테이블이 관리되지 않는다.
* Command Line이 아닌 다른 방법으로 Job을 실행하기 어렵다.
    * 실행하려면 시스템 변수를 동적으로 변경 시키도록 구성이 필요하다.
* Late Binding 기능을 사용할 수 없다.

## (중요) @Bean과 @StepScope 동시 사용시 주의 사항
* listener 어노테니션을 사용할 수 있도록 구현 클래스를 리턴해야한다.
```java
@Bean
public ItemReader<Person> reader() {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("firstName", "Jang");

    JpaPagingItemReader<Persion> reader = JpaPagingItemReader<>();
    reader.setEntityManagerFactory(entityManager);
    reader.setQueryString("select p from Persion p where p.firstName=:firstName");
    reader.setParameterValues(paramMap); 
    reader.setPageSize(10);

    return reader;
}

@Bean
public JpaPagingItemReader<Person> reader() {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("firstName", "Jang");

    JpaPagingItemReader<Persion> reader = JpaPagingItemReader<>();
    reader.setEntityManagerFactory(entityManager);
    reader.setQueryString("select p from Persion p where p.firstName=:firstName");
    reader.setParameterValues(paramMap); 
    reader.setPageSize(10);

    return reader;
}
```


## Chunk 지향 처리
* Chunk 지향 처리
    * 한 번에 하나씩 데이터를 읽어 Chunk라는 덩어리를 만든 뒤, Chunk 단위로 트랜잭션을 다루는 것
    * Reader와 Processor에서는 1건씩 다뤄지고, Writer에선 Chunk 단위로 처리
* Page Size vs Chunk Size
    * Chunk Size는 한번에 처리될 트랜잭션 단위이며, Page Size는 한번에 조회할 Item 양을 애기한다.
    

## ItemReader
* 대표적인 구현체인 JdbcPagingItemReader의 경우 ItemReader와 ItemStream 인터페이스를 구현하고 있다.
    * ItemReader
        * 데이터를 읽어오는 메소드
    * ItemStream
        * 주기적으로 상태를 저장하고 오류가 발생하면 해당 상태에서 복원하기 위한 마커 인터페이스
        * 배치 프로세스의 실행 컨텍스트와 연계하여 ItemReader의 상태를 저장하고 실패한 곳에서 다시 실행할 수 있게 해주는 역활
            * open(), close(), update()로 구성되어 있으며 update()를 사용하면 배치 처리의 상태를 업데이트
            
### CursorItemReader
* 데이터베이스와 커넥션을 맺은 후, Cursor를 한칸씩 옮기면서 지속적으로 데이터를 가져옵니다.



## ItemProcessor
* ItemProcessor는 필수가 아니지만 Reader, Writer에서 비지니스 코드가 섞이는 것을 방지해주는 역활
* ItemProcessor의 역활
    * 변환
        * Reader에서 읽은 데이터를 원하는 타입으로 변환해서 Writer로 전달
    * 필터
        * Reader에서 넘겨준 데이터를 Writer로 넘겨줄지 결정
        * null을 반환할 경우 Writer로 전달되지 않음            
        
        
        
## Jenkins
* Build
    * [Add build step] 버튼을 클릭
    * [Invoke Gradle script] 선택
    * [Use Gradle Wrapper] 선택
    * [Make gradlew executable] 체크
    * Wrapper location : ./
    * Tasks
    ```shell script
    :application:clean
    :application:spring-boot-batch:build
    -Dspring.profiles.active=stage
    ``` 
### Publish over SSH
* Jenkins 관리 - 시스템 설정 - Publish over SSH
    * Passphrase : 접속하려는 서버의 패스워드
    * SSH Servers.Name : 서버 이름 (임의 지정 가능)
    * SSH Servers.Hostname : 접속하려는 서버 IP
    * SSH Servers.Username : 접속하려는 서버의 로그인 아이디
* 프로젝트 - 구성 - Build - Send files or execute commands over SSH 
    * Name : 시스템 관리에서 생성 한 서버 중에서 선택
    * Source files : 전달하려는 파일 정보
    * Remove prefix : 전달 후 삭제하려는 폴더
    * Remote directory : 대상 서버에서 복사 대상이 되는 폴더