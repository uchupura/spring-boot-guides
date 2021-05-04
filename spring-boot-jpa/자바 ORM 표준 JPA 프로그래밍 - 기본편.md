## 페치 조인 (fetch join)
* SQL 조인 종류X
* JPQL에서 성능 최적화를 위해 제공하는 기능
* 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
* join fetch 명령어 사용
* 페치 조인 ::= [ LEFT [OUTER] | INNER ] JOIN FETCH 조인경로
* **쿼리로 내가 원하는 객체 그래프를 한번에 조회한다는 것을 직접 명시적으로 동적인 타이밍에 정할 수 있다.**
* fetch join시 Fetch 타입이 LAZY인 연관 관계 객체들은 이미 조회가 되어서 영속성 컨텍스트에 들어가 있는 상태가 된다.

### 엔티티 페치 조인
* 회원을 조회하면서 연관된 팀도 함께 조회(SQL 한 번에)
* SQL을 보면 회원 뿐만 아니라 팀(T.*)도 함께 SELECT
* [JPQL]
    * select m from Member m join fetch m.team
* [SQL]
    * SELECT M.\*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID=T.ID

<img width="80%" src="https://user-images.githubusercontent.com/41175779/117011934-55391200-ad29-11eb-80a5-a25bec449854.png"/>\

### 페치 조인 사용 코드
<img width="80%" src="https://user-images.githubusercontent.com/41175779/117017422-8bc55b80-ad2e-11eb-8efc-5e8412f5e672.png"/>\

### 컬렉션 페치 조인
* 일대다 관계, 컬렉션 페치 조인
```sql
[JPQL]
select t
from Team t join fetch t.members
where t.name = ‘팀A'

[SQL]
SELECT T.*, M.*
FROM TEAM T
INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
WHERE T.NAME = '팀A'
```
<img width="80%" src="https://user-images.githubusercontent.com/41175779/117017929-feced200-ad2e-11eb-9481-29f2458b8997.png"/>\
* 팀A 입장에서는 MEMBER가 2명이고 JOIN을 하게 되면 2줄이 생성된다.
* 첫 번째 팀A를 영속성 컨텍스트에 올리고, 두 번째 팀A를 영속성 컨텍스트에 올리시점에 이미 올라와 있으므로 같은 객체를 바라보게 된다.
* Collection Join시에는 Collection의 개수만큼 결과값이 뻥튀기된다.
### 컬렉션 페치 조인 사용 코드
```java
String jpql = "select t from Team t join fetch t.members where t.name = '팀A'"
List<Team> teams = em.createQuery(jpql, Team.class).getResultList();
for(Team team : teams) {
    System.out.println("teamname = " + team.getName() + ", team = " + team);
    for (Member member : team.getMembers()) {
        //페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
        System.out.println(“-> username = " + member.getUsername()+ ", member = " + member);
    }
}
```
teamname = 팀A, team = Team@0x100  
-> username = 회원1, member = Member@0x200  
-> username = 회원2, member = Member@0x300  
teamname = 팀A, team = Team@0x100  
-> username = 회원1, member = Member@0x200  
-> username = 회원2, member = Member@0x300  

### 페치 조인과 DISTINCT
* SQL의 DISTINCT는 중복된 결과를 제거하는 명령
* JPQL의 DISTINCT 2가지 기능 제공
    * SQL에 DISTINCT를 추가
    * 애플리케이션에서 엔티티 중복 제거
```sql
select distinct t
from Team t join fetch t.members
where t.name = ‘팀A’
```
* SQL에 DISTINCT를 추가하지만 데이터가 다르므로 SQL 결과에서 중복제거 실패
<img width="80%" src="https://user-images.githubusercontent.com/41175779/117015886-1f962800-ad2d-11eb-98d5-8ee72c76e7c5.png"/>\

* DISTINCT가 추가로 애플리케이션에서 중복 제거 시도
* 같은 식별자를 가진 Team 엔티티 제거
<img width="80%" src="https://user-images.githubusercontent.com/41175779/117018802-cda2d180-ad2f-11eb-8ceb-53b79354efab.png"/>\
[DISTINCT 추가시 결과]  
teamname = 팀A, team = Team@0x100  
-> username = 회원1, member = Member@0x200  
-> username = 회원2, member = Member@0x300  

### 페치 조인과 일반 조인의 차이
* 일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음
```sql
[JPQL]
select t
from Team t join t.members m
where t.name = ‘팀A'

[SQL]
SELECT T.*
FROM TEAM T
INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
WHERE T.NAME = '팀A'
```
* JPQL은 결과를 반환할 때 연관관계 고려X
* 단지 SELECT 절에 지정한 엔티티만 조회할 뿐
* 여기서는 팀 엔티티만 조회하고, 회원 엔티티는 조회X
* 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
* 페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념

### 페치 조인 실행 예시
* 페치 조인은 연관된 엔티티를 함께 조회함
```sql
[JPQL]
select t
from Team t join fetch t.members
where t.name = ‘팀A'

[SQL]
SELECT T.*, M.*
FROM TEAM T
INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
WHERE T.NAME = '팀A'
```



* 페치 조인 대상에는 별칭을 줄 수 없다.
    * 하이버네이트는 가능, 가급적 사용 X
    * 페치 조인은 기본적으로 나와 연관된 모든 객체를 다 긇어오는 것이다.
    * JPA에 의도한 설계는 team.getMember()일 경우 모든 Member에 다 접근이 가능해야한다.
    * where절에 아래와 같이 조건을 달면 Team 입장에서 모든 Member에 접근이 불가능해진다.
    * 만약 특정 Member 객체 리스트 검색이 필요한 경우에는 Team을 통해서 Member에 접근하는 것이 아니라, Member 객체 리스트를 조회하는 것을 따로 구현해야 한다.
    
```sql
select t from Team t join fetch t.member where m.age > 10
select t from Team t join fetch t.member m join fetch m.team
```
* 둘 이상의 컬렉션은 페치 조인 할 수 없다.
* 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
    * 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능 (데이터 뻥튀기가 안됨)
    * 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)
