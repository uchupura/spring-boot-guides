### AOP (Aspect-Oriented Programming) ?
- 관점 지향 프로그래밍
- 애플리케이션 전체에 걸쳐 사용되는 기능을 재사용하도록 지원
- 서비스 관점이 아닌 부가기능적인 관점에서 보았을 때 공통된 요소를 추출하는 것 (크로스 컷팅 - Cross-Cutting)
- 부가 기능의 모듈화
- DI란?
    - 애플리케이션 모듈들 간의 결합도를 낮추도록 지원

### AOP 장점?
- 애플리케이션 전체에 흩어진 공통 기능을 하나의 장소에서 관리
- 다른 서비스 모듈들이 본인의 역할에만 충실하고 그 외 사항들은 신경쓰지 않아도 된다는 점

### AOP 용어
#### 타겟 (Target)
- 부가 기능을 부여할 대상

#### 에스펙트 (Aspect)
- 객체 지향 모듈을 오브젝트라고 부르는 것 처럼 부가 기능 모듈을 에스펙트라고 부름
- 구성 요소
    - 어드바이스 : 부가될 기능을 정의
    - 포인트컷 : 어드바이스를 어디에 적용할지를 결정

#### 어드바이스 (Advice)
- 실질적인 부가기능을 담은 구현체
- Aspect가 '무엇'을 '언제' 할지를 정의
- Advice 타입
    - @Before : Advice 타겟 메소드가 호출되기 전에 Advice 기능 수행 (JoinPoint joinPoint)
    - @After : 타겟 메소드의 결과에 관계없이 타겟 메소드가 완료되면 Advice 기능 수행 (JoinPoint joinPoint)
    - @AfterReturning : 타겟 메소드가 성공적으로 결과값을 반환 후에 Advice 기능 수행 (JoinPoint joinPoint)
    - @AfterThrowing : 타겟 메소드가 수행 중 예외를 던지게 되면 Advice 기능 수행 (JoinPoint joinPoint)
    - @Around : Advice가 타겟 메소드를 감싸서 타겟 메소드 호출전과 후에 Advice 기능 수행 (ProceedingJoinPoint)
        - 반드시 proceed() 메소드가 호출되어야 함

#### 포인트컷 (PointCut)
- 부가 기능이 적용될 대상(메소드)를 선정하는 방법

#### 조인포인트 (JoinPoint)
- Advice가 적용될 수 있는 위치

#### 프록시 (Proxy)
- Target을 감싸서 Target의 요청을 대신 받아주는 랩핑(Wrapping) 오브젝트

#### 위빙 (Weaving)
- 지정된 객체에 Aspect를 적용해서 새로운 Proxy 객체를 생성하는 과정
- Runtime에서 Proxy 객체를 생성


### 포인트컷 표현식
#### 명시자
- execution : Advice를 적용 할 메서드를 명시할 때 사용
- within : 특정 타입에 속하는 메서드를 JoinPoint로 설정되도록 명시할 때 사용
- bean : 스프링 빈을 JoinPoint로 사용

#### execution 명시자
```
execution([수식어] 리턴타입 [클래스이름].이름(파라미터)
``` 
- 수식어 : public, private 등 수식어를 명시합니다. (생략 가능)
- 리턴타입 : 리턴 타입을 명시합니다.
- 클래스이름 및 이름 : 클래스이름과 메서드 이름을 명시합니다.
- 파라미터 : 메서드의 파라미터를 명시합니다.
- " * " : 모든 값을 표현합니다.
- " .. " : 0개 이상을 의미합니다.
- " ..* " : 해당 패키지 및 하위 패키지
##### 예시
- execution(public Integer com.edu.aop.*.*(*))
    - com.edu.aop 패키지에 속해있고, 파라미터가 1개인 모든 메서드
- execution(* com.edu..*.get*(..))
    - com.edu 패키지 및 하위 패키지에 속해있고, 이름이 get으로 시작하는 파라미터가 0개 이상인 모든 메서드 
- execution(* com.edu.aop..*Service.*(..))
    - com.edu.aop 패키지 및 하위 패키지에 속해있고, 이름이 Service로 끝나는 인터페이스의 파라미터가 0개 이상인 모든 메서드
- execution(* com.edu.aop.BoardService.*(..))
    - com.edu.aop.BoardService 인터페이스에 속한 파마리터가 0개 이상인 모든 메서드
- execution(* some*(*, *))
    - 메서드 이름이 some으로 시작하고 파라미터가 2개인 모든 메서드

#### within 명시자
- execution 지정자에서 클래스/인터페이스까지만 적용된 경우
##### 예시
- within(com.edu.aop.SomeService)
    - com.edu.aop.SomeService 인터페이스의 모든 메서드
- within(com.edu.aop.*)
    - com.edu.aop 패키지의 모든 메서드
- within(com.edu.aop..*)
    - com.edu.aop 패키지 및 하위 패키지의 모든 메서드
    
#### bean 명시자
##### 예시 
- bean(someBean)
    - 이름이 someBean인 빈의 모든 메서드
- bean(some*)
    - 빈의 이름이 some으로 시작하는 빈의 모든 메서드
    
    
### 재사용 가능한 포인트컷 생성
```
@Aspect
public class Performance {

    @Pointcut("execution(* com.blogcode.board.BoardService.getBoards(..))")
    public void getBoards(){}

    @Pointcut("execution(* com.blogcode.user.UserService.getUsers(..))")
    public void getUsers(){}

    @Around("getBoards() || getUsers()")
    public Object calculatePerformanceTime(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        try {
            long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();

            System.out.println("수행 시간 : "+ (end - start));
        } catch (Throwable throwable) {
            System.out.println("exception! ");
        }
        return result;
    }
}
```

### 타겟 메소드에 전달된 인자(Argument)값 사용
```
@Aspect
@Component // @Bean과 동일하게 Spring Bean 등록 어노테이션
public class UserHistory {

    @Autowired
    private HistoryRepository historyRepository;

    @Pointcut("execution(* com.blogcode.user.UserService.update(*)) && args(user)")
    public void updateUser(User user){}

    @AfterReturning("updateUser(user)")
    public void saveHistory(User user){
        historyRepository.save(new History(user.getIdx()));
    }
}
```