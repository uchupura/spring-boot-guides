# oauth2 데모 프로젝트

#### 테스트 환경
* redis 실행 
    * docker-compose up -d
* redis 종료
    * docker-compose down


#### 로그인
* Authorization 헤더에는 Basic Auth 타입을 적용
    * my-client1, my-secret1을 db.changelog-master.yaml을 통해서 구동 시 oauth_client_details 테이블에 등록됨
    * Username(my-client1), Password(my-secret1)을 암호화해서 사용
    * https://www.blitter.se/utils/basic-authentication-header-generator : Basic Auth Header Generator URL
```shell script
$ curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=password&username=admin&password=admin&scope=read' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic bXktY2xpZW50MTpteS1zZWNyZXQx'

{"access_token":"0cae1339-e216-4b50-afd4-763163f94890","token_type":"bearer","refresh_token":"755fc49e-0437-41c3-85db-d87b84b651ea","expires_in":599,"scope":"read"}
```

#### 로그아웃
```shell script
curl --location --request DELETE 'http://localhost:8080/api/tokens' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer [로그인 성공 시 응답값으로 받은 Access Token]'

{"count":1,"data":["f1ebf73f-87ab-4d14-88db-a7357d1c740b"]}
```

#### Refresh Token을 이용해서 토큰 갱신 
```shell script
curl --location --request POST 'http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=[로그인 성공 시 응답값으로 받은 Refresh Token]' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic bXktY2xpZW50MTpteS1zZWNyZXQx'
```

#### 토큰 체크
```shell script
curl --location --request POST 'http://localhost:8080/oauth/check_token?token=0cae1339-e216-4b50-afd4-763163f94890' \
--header 'Content-Type: application/json'

{"active":true,"exp":1603274049,"user_name":"admin","authorities":["ROLE_ADMIN"],"client_id":"my-client1","scope":["read"]}
```

#### 토큰 조회
```shell script
curl --location --request GET 'http://localhost:8080/api/tokens/[Authorization Username]' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer [로그인 성공 시 응답값으로 받은 Access Token]'
```