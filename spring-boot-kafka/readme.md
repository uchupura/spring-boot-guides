# Kafka

## Kafka란?
- 여러 대의 분산 서버에서 대용량의 메세지를 처리하는 분산 메세징 시스템
- 특징
    - 스트리밍 데이터를 다루는 미들웨어
    - 확장성과 가용성
    - 데이터 영속성
    - Pub/Sub 모델 지원
    
## Kafka의 구성 요소 및 특징
### Topic과 Partition
- 메세지는 Topic으로 분류되고, Topic은 여러 개의 Partition으로 나눠 질 수 있다.
- Partition 내의 한 칸을 로그라고 하고, 데이터는 한 칸의 로그에 append된다.
- 메세지의 상대적인 위치를 나타내는 것이 offset이다.
- 여러 개의 Partition을 나누는 이유?
    - 여러 개의 Partition을 두면 쓰기가 병렬로 처리됨

NOTE: 한 번 늘린 파티션은 절대로 줄일 수 없기 때문에 파티션을 늘릴 때 충분히 고려되어야 한다.

<img width="80%" src="https://user-images.githubusercontent.com/41175779/124350836-dc273f80-dc31-11eb-85fb-e994d16fd072.png"/>

### Producer와 Consumer
- Producer
    - 메세지를 생산하는 주체
    - 메세지를 만들어서 Topic에 메세지를 쓴다.
    - Producer는 Consumer의 존재를 알 지 못한다.
- Consumer
    - 메세지를 소비하는 주체
    - 해당 Topic을 구독함으로써 자기 스스로 조절해가며 메세지를 소비
    - Consumer는 Producer의 존재를 알 지 못한다.
    - 소비를 했다는 표시는 각 파티션에 존재하는 offset의 위치를 통해서 소비했던 offset의 위치를 관리
        - fail-over : offset을 통해서 consumer가 죽었다 살아나도 전에 마지막으로 읽었던 위치에서부터 다시 읽어들일 수 있다.
        
   
### Consumer Group
- consumer들의 묶음
- 해당 Topic의 파티션은 Consumer Group과 1:n 매칭을 해야한다.
- Consumer Group이 존재하는 이유?
    - Consumer Group은 하나의 Topic에 대한 책임을 갖고 있다. 
    - 리밸런스된 상황 : 그룹 내의 어떤 Consumer가 다운이 된다면 해당 Consumer가 소비하고 있던 Partition에 대해 소비할 수 없는 상황이 발생
    - 리밸런스된 상황에서 Partition 재조정을 통해서 다른 Consumer가 해당 Partition 소비를 이어가게된다.

<img width="80%" src="https://user-images.githubusercontent.com/41175779/124351394-f9a9d880-dc34-11eb-8b71-99d310d3a568.png"/>    

     
### Broker와 Zookepper
- Broker
    - Kafka의 서버를 지칭
    - broker.id=1..n으로 함으로써 동일한 노드에 여러 개의 broker 서버를 띄울 수도 있다.
- Zookeeper
    - 분산 메세지 큐 정보를 관리해주는 기능을 수행
    - Kafka를 띄우기 위해서는 Zookeeper가 반드시 실행되어야 한다.
    
### Replication
- local에 broker를 3대 띄우고 (replica-factor=3)로 복제하는 경우
    - broker 3대에서 하나만 leader가 되고, 나머지 2대는 follower가 된다.
    - producer가 메세지를 쓰고, consumer가 메세지를 읽는 건 오로지 leader 역할이다.
    - follower들의 역할?
        - leader와 싱크를 맞추다가 leader가 죽었을 경우, 나머지 follower 중에 하나가 leader로 선출된다.        

## Docker로 Kafka 실행
### docker-compose
```
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka:2.12-2.5.0
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```


