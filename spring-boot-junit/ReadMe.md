### MockMvc
실제로 네트워크에 연결하지 않고도 API 테스트가 가능하도록 모킹된 객체


### MockMvc 모드
#### 사용자 정의 DI 컨테이너 모드
```
webAppContextSetup(WebApplicationContext context)
```
파라미터로 주어진 초기화된 WebApplicationContext를 사용하여 MockMvc 인스턴스를 만든다.
#### 단독모드
```
standaloneSetup(Object... controllers)
```
Controller 인스턴스를 하나 이상 등록하고 Spring MVC 인프라를 프로그래밍 방식으로 구성하여 MockMvc 인스턴스를 만든다.