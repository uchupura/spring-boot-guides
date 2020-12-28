# Spring Cloud Feign

### OpenFeign 프로젝트
RestTemplate 호출 등을 JPA Repository처럼 interface로 단순히 추상화 한 프로젝트

### @EnableFeignClient
@FeignClient 어노테이션이 있는 interface를 찾아서 등록해주는 역할
기본적으로 @EnableFeignClien 어노테이션이 있는 하위 패키지를 탐색하고 만약 해당 어노테이션이 하위 패키지에 존재하지 않는다면 basePackage 또는 basePackageClasses 속성을 이용해서 커스터마이징 필요

### Reference
* (Spring Cloud) Feign - https://supawer0728.github.io/2018/03/11/Spring-Cloud-Feign/
* 우아한 feign 적용기 - https://woowabros.github.io/experience/2019/05/29/feign.html
* feign 좀더 나아가기 - https://woowabros.github.io/experience/2019/12/20/feign2.html
* feign-apply-experience-sample - https://github.com/woowabros/feign-apply-experience-sample
* [MSA] Spring Cloud Feign - 커스터 마이징 설정편 - https://sabarada.tistory.com/116?category=822738



 