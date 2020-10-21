# Redis 데모 프로젝트


#### Redis 추가 테스트
```shell script
curl --location --request POST 'http://localhost:8080' \
--header 'Content-Type: application/json' \
--data '{
    "id": "uchupura",
    "accessToken": "31a54779-86c5-473d-b2f4-090d73015b96",
    "refreshToken": "23108f08-c118-473f-b532-7bceaa6c98e6",
    "expiresIn": 599,
    "scope": "read"
}'
```

#### Redis 검색 테스트
```shell script
curl --location --request GET 'http://localhost:8080/uchupura' \
--header 'Content-Type: application/json' 
```

#### Redis 변경 테스트
```shell script
curl --location --request PUT 'http://localhost:8080' \
--header 'Content-Type: application/json' \
--data '{
    "id": "uchupura",
    "accessToken": "31a54779-86c5-473d-b2f4-000000000000",
    "refreshToken": "23108f08-c118-473f-b532-000000000000",
    "expiresIn": 600,
    "scope": "read"
}'
```

#### Redis 삭제 테스트
```shell script
curl --location --request DELETE 'http://localhost:8080/uchupura' \
--header 'Content-Type: application/json'
```