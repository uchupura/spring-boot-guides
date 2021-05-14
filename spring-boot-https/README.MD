### keytool을 이용한 SSL 인증서 생성
```
$ keytool -genkey -alias bns-ssl -storetype JKS -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
```
* -alias bns-ssl
    * key alias를 bns-ssl로 지정합니다.
* -keystore keystore.p12
    * key store 이름을 keystore.p12로 지정합니다.
    
### 스프링 부트 설정
```
server:
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-password: jang3827!
    key-store-type: JKS
    key-alias: bns-ssl
  port: 8443

server.port.http: 8080
```

### 멀티 커넥터 설정
```java
@Configuration
public class ServletConfig {
    @Value("${server.port.http}")
    private int serverPortHttp;
    @Value("${server.port}")
    private int serverPortHttps;

    /**
     * 멀티 커넥터
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(serverPortHttp);
        return connector;
    }
}
```

### HTTP 요청을 HTTPS로 리다이렉트 시키기

```java
@Configuration
public class ServletConfig {
    @Value("${server.port.http}")
    private int serverPortHttp;
    @Value("${server.port}")
    private int serverPortHttps;

    /**
     * HTTP 요청을 HTTPS로 리다이렉트 시키기
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
    
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                // 특정 경로에 대해서만 https로 리다이렉트시키기
                // collection.addPattern("/test");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
     }
}
```