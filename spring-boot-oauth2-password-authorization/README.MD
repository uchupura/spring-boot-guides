## 실행 방법

### 01. docker container 실행
```
docker-compose up -d
```

### 02. database table 생성
* schema.sql 파일을 이용해서 테이블 생성
```
initialization-mode: always
```

### 03. grant_type 및 authority 확인
* oauth_client_details 테이블의 authorized_grant_types 확인
    * password의 경우 : password로 입력
* user 테이블의 role 확인


