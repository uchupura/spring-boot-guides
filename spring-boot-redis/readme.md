# Redis

## Redis Cluster
### 성능 향상 방법
- 스케일 업 (Scale Up)
    - 단일 머신에 CPU, Memory를 추가해서 성능을 향상 시키는 방법
- 스케일 아웃 (Scale Out)
    - 적절한 성능의 머신을 추가해서 전체적인 성능을 향상 시키는 방법
        - 소프트웨어에서 scale out을 지원해야 가능
        
### 대량의 데이터를 처리, 저장하는 방법
- 데이터 파티셔닝 (Data Partitioning)
    - 대량의 데이터를 처리하기 위해 DBMS 안에서 분할하는 방식
    <img width="80%" src="https://user-images.githubusercontent.com/41175779/124379174-67670a80-dcf0-11eb-9335-7b428b77ccd8.png"/>
- 데이터 샤딩 (Data Sharding)
    - 대량의 데이터를 처리하기 위해 여러 개의 DBMS에 분할하는 방식
    <img width="80%" src="https://user-images.githubusercontent.com/41175779/124379244-c9c00b00-dcf0-11eb-868a-5e94e5979aca.png"/>    

### Redis Cluster Architecture
- 단일 장애점이 없는 토폴로지인 Mesh 토폴로지를 사용
- Clone 노드를 포함한 모든 노드가 서로를 확인하고 정보를 주고 받음 -> Fully Connected Mash Topology.
- 클라이언트는 어느 노드든지 접속해서 클러스터 구성 정보(슬롯 할당 정보)를 가져와서 보유하며, 입력 되는 키(key)에 따라 해당 노드에 접속하여 처리
- 일부 노드가 다운되어도 레디스 클러스터에 영향을 주지 않지만, 과반수 이상의 노드가 다운되면 레디스 클러스터는 멈추게 됩니다.
- 데이터를 처리하는 마스터 노드는 1개 이상의 복제 노드를 가질 수 있음
<img width="80%" src="https://user-images.githubusercontent.com/41175779/124379521-92eaf480-dcf2-11eb-9e51-fc462e52d9a7.png"/>

### Redis Cluster 키-노드 할당 방식
- 해시 할당 (Hash Assignment) 방식 사용
    - 노드 데이터와 무관하고 모든 노드에 일정하게 데이터가 할당
    - 입력 된 키에 해시 함수(Hash Function)를 적용하여 0부터 16384까지는 숫자를 생성
    - 레디스 클러스터는 16384개의 슬롯을 사용
        - 슬롯의 번호는 0 ~ 16383이고 레디스 노드가 3개일 경우 아래와 같이 할당
            - 1번 노드 : 0-5460
            - 2번 노드 : 5461-10922
            - 3번 노드 : 10923-16383





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