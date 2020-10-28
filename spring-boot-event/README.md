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

## Statement Cache
* 많은 커넥션 풀 라이브러리들은 PreparedStatement 캐시를 지원하지만 HikariCP는 지원하지 HikariCP는 지원하지 않음
* 커넥션 풀 레이어에서 PreparedStatement는 각 커넥션마다 캐싱됨
    * ex) 어플리케이션에서 250개의 공통적인 쿼리를 캐싱하고 있고 pool size가 20이라면 5000개의 쿼리 실행 계획이 캐싱됨 
    
### cachePrepStmts
* 캐시가 기본적으로 비활성화되어 있는 경우 위 매개 변수 중 어느 것도 영향을 받지 않음

### prepStmtCacheSize
* 각각의 커넥션마다 드라이버가 캐싱 할 PreparedStatement 개수 설정 (default:25)

### prepStmtCacheSqlLimit
* 드라이버가 캐싱 할 수 있는 최대 PreparedStatement 개수 설정 (default:256)

### useServerPrepStmts
* MySQL 최신 버젼에서 server-side PreparedStatement 지원

---
## SpEL
### SpEL이란?
Spring Expression Language의 약자로 런타임시에 객체 그래프를 조회하고 조작하는 강력한 표현 언어
메서드 호출, 프로퍼티 접근, 생성자 호출 같은 넓은 범위의 기능을 지원

### ExpressionParser 인터페이스
* 문자열 표현을 파싱하는 역활

### Expression 인터페이스
* ExpressionParser에서 정의한 문자열 표현을 평가하는 역활
