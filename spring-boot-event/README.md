# HikariCP 옵션
## Properties
### auto-commit
* auto-commit 설정 (default:true)

### connection-timeout
* 커넥션 풀에서 커넥션을 얻어오기전까지 기다리는 최대 시간
* 허용 가능한 wait time을 초과하면 SQLException 발생 (default:30000 -> 30초)

### idle-timeout
* 커넥션 풀에서 일을 안하는 커넥션을 유지하는 시간 (default:600000 -> 10분)

### minimum-idle
* 커넥션 풀에서 일을 하지 않아도 유지되는 최소 커넥션 수

### max-lifetime
* 커넥션 풀에서 살아 있을 수 있는 최대 수명 시간 (default:1800000 -> 30분)
* 커넥션을 유지하다가 만료되면 새로운 커넥션을 생성하는 사이클이 반복됨
* 사용중이지 않을 때만 제거됨
* 커넥션 풀 전체가 아닌 커넥션 별로 적용되는데 그 이유는 풀에서 대량으로 커넥션들이 제거되는 것을 방지하기 위함
* 데이터베이스나 인프라에 적용 된 connection time limit보다는 작아야 함

### connection-test-query
* 커넥션이 유효한 지 검사할 때 사용할 쿼리 지정 (SELECT 1)
* JDBC4 드라이버를 지원한다면 JDBC4의 Connection.isValid()를 사용해서 유효성 검사 수행 
* JDBC4 드라이버를 지원하지 않는 환경에서 이 값을 설정하지 않으면 error 레벨 로그 생성 (default:none)

### validationTimeout
* valid 쿼리를 통해 커넥션이 유효한지 검사할 때 사용되는 timeout (default:5000)

### pool-name
* 사용자가 커넥션 풀의 이름을 지정
* 로깅이나 JMX management cnosole에 표시되는 이름 (default:auto-generated) 

### maximum-pool-size
* 유휴 상태와 사용중인 커넧녀을 포함해서 풀이 허용하는 최대 커넥션 개수
* 풀이 이 크기에 도달하고 유휴 커넥션이 없을 때 connection-timeout이 지날 때까지 getConnection() 호출은 블로킹 된다. (default:10)

### initialization-fail-timeout
* 커넥션 풀을 초기화할 때 성공적으로 수행할 수 없을 경우 빠르게 실패되도록 설정

### readOnly
* 커넥션 풀에서 커넥션을 획득할 때 read-only모드로 가져옴 (default:false)

### driver-class-name
* HikariCP는 jdbcUrl을 참조하여 자동으로 driver를 설정하려고 시도

### leak-detection-threshold
* 커넥션이 누수 가능성이 있다는 로그 메시지를 출력하기 전에 커넥션이 풀에서 벗어날 수 있는 시간을 설정 (default:2000)

---

# Statement Cache
* 많은 커넥션 풀 라이브러리들은 PreparedStatement 캐시를 지원하지만 HikariCP는 지원하지 HikariCP는 지원하지 않음
* 커넥션 풀 레이어에서 PreparedStatement는 각 커넥션마다 캐싱됨
    * ex) 어플리케이션에서 250개의 공통적인 쿼리를 캐싱하고 있고 pool size가 20이라면 5000개의 쿼리 실행 계획이 캐싱됨 
    
## cachePrepStmts
* 캐시가 기본적으로 비활성화되어 있는 경우 위 매개 변수 중 어느 것도 영향을 받지 않음

## prepStmtCacheSize
* 각각의 커넥션마다 드라이버가 캐싱 할 PreparedStatement 개수 설정 (default:25)

## prepStmtCacheSqlLimit
* 드라이버가 캐싱 할 수 있는 최대 PreparedStatement 개수 설정 (default:256)

## useServerPrepStmts
* MySQL 최신 버젼에서 server-side PreparedStatement 지원

---
# SpEL
## SpEL이란?
Spring Expression Language의 약자로 런타임시에 객체 그래프를 조회하고 조작하는 강력한 표현 언어
메서드 호출, 프로퍼티 접근, 생성자 호출 같은 넓은 범위의 기능을 지원

## ExpressionParser 인터페이스
* 문자열 표현을 파싱하는 역활

## Expression 인터페이스
* ExpressionParser에서 정의한 문자열 표현을 평가하는 역활


---
# EventListener
## @EventListener
* Synchronous(동기)로 처리된다.
* Transaction이 EventListener에도 같이 묶여서 처리된다.
## @TransactionalEventListener
* Asynchronous(비동기)로 처리된다.
* Transaction이 EventListener와 별도로 불리되기 때문에 EventListener에서 Exception이 발생해도 기존 서비스의 Transaction에 영향을 미치지 않는다.
* phase 종류
    * BEFORE_COMMIT
    * AFTER_COMPLETION
    * AFTER_COMMIT
    * AFTER_ROLLBACK
    

---
# 도메인형 디렉토리 구조
* Root Package
    * Application 클래스
    * domain 패키지 : 도메인을 담당하는 패키지
        * coupon : Coupon, CouponDTO, CouponAPI, CouponService, CouponRepository, xxxException
        * member : Member, MemberDTO, MemberAPI, MemberService, MemberRepository, xxxException
        * model : Domain Entity 객체들이 공통적으로 사용하는 클래스들로 구성 ex) Embeddable, Enum
    * global 패키지 : 프로젝트 전방위적으로 사용되는 패키지
        * common : 공통으로 사용되는 Value 객체 ex) 페이징 처리 Request 클래스, 공통 응답을 주는 Reponse 클래스
        * config : 스프링 각종 설정
        * error : 각종 예외 처리 클래스
        * util : 유틸성 클래스
    * infra 패키지 : 외부 서비스 연동을 위한 패키지
        
---
# Spring AOP PointCut 표현식
## PointCut 명시자
### execution : Advice를 적용할 메서드를 명시 할 때 사용
```
execution([수식어] 리턴타입 [클래스이름].이름(파라미터)

# 수식어 : public, private 등 수식어를 명시합니다. (생략 가능)
# 리턴타입 : 리턴 타입을 명시합니다.
# 클래스이름 및 이름 : 클래스이름과 메서드 이름을 명시합니다. (클래스 이름은 풀 패키지명으로 명시해야합니다. 생략도 가능)
# 파라미터 : 메서드의 파라미터를 명시합니다.
# " * " : 모든 값을 표현합니다.
# " .. " : 0개 이상을 의미합니다.
```
* execution(public Integer com.edu.aop.*.*(*))
    * com.edu.aop 패키지에 속해있고, 파라미터가 1개인 모든 메서드
* execution(* com.edu..*.get*(..))
    * com.edu 패키지 및 하위 패키지에 속해있고, 이름이 get으로 시작하는 파라미터가 0개 이상인 모든 메서드 
* execution(* com.edu.aop..*Service.*(..))
    * com.edu.aop 패키지 및 하위 패키지에 속해있고, 이름이 Service르 끝나는 인터페이스의 파라미터가 0개 이상인 모든 메서드
* execution(* com.edu.aop.BoardService.*(..))
    * com.edu.aop.BoardService 인터페이스에 속한 파마리터가 0개 이상인 모든 메서드
* execution(* some*(*, *))
    * 메서드 이름이 some으로 시작하고 파라미터가 2개인 모든 메서드 

### within : 특정 타입에 속하는 메서드를 JoinPoint로 설정되도록 명시 할 때 사용  
* within(com.edu.aop.SomeService)
    * com.edu.aop.SomeService 인터페이스의 모든 메서드
* within(com.edu.aop.*)
    * com.edu.aop 패키지의 모든 메서드
* within(com.edu.aop..*)
    * com.edu.aop 패키지 및 하위 패키지의 모든 메서드
* within(com..*)
    * com 패키지 및 하위 패키지의 모든 메서드
* within(org.springframework.validation..*)
 
### bean : 스프링 버전 2.5 버전부터 지원하기 시작했으며, 스프링 빈을 이용하여 JoinPoint를 설정
* bean(someBean)
    * 이름이 someBean인 빈의 모든 메서드
* bean(some*)
    * 빈의 이름이 some으로 시작하는 빈의 모든 메서드
    

---
# 이벤트 처리
## 도메인 이벤트
* AbstractAggregateRoot를 사용하여 도메인 이벤트를 발생 시키는 경우 @EventListener와 @TransactionalEventListener의 동작은 어플리케이션 이벤트와 다르게 동작한다.
* @EventListener
    * save() 함수가 호출 될 때 이벤트 리스너가 이벤트를 수신한다.
    * 리스너에서 예외 발생 시 이벤트를 호출 한 도메인의 트랜잭션은 롤백된다.
* @TransactionalEventListener
    * Transaction이 종료되는 시점에 이벤트 리스너가 이벤트를 수신한다.
    * 리스너에서 예외 발생 시 이벤트를 호출 한 도메인의 트랜잭션은 롤백되지 않는다.

## 어플리케이션 이벤트
* @EventListener
    * 동기 방식으로 이벤트 리스너가 이벤트를 수신한다.
* @TransactionalEventListener
    * Transaction이 종료되는 시점에 이벤트 리스너가 이벤트를 수신한다.

 