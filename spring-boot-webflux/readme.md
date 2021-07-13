# WebFlux

## Flux
- Reactive Streams의 Publisher를 구현한 N 요소의 스트림을 표현하는 Reactor 클래스
- 기본적으로 text/plain으로 응답이 반환되지만, 아래와 같이 반환 할 수도 있다.
    - Server-Sent Event
    - JSON Stream
- Server-Send Event로 반환하기 위해서 Accept 헤더에 text/event-stream을 지정
```
$ curl -i localhost:8080 -H 'Accept: text/event-stream'
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: text/event-stream;charset=UTF-8

data:Hello

data:World
```

- JSON Stream으로 반환하려면 Accept 헤더에 application/stream+json을 지정
    - 반환되는 데이터가 문자열 스트림인 경우 text/plain과 다르지 않음
```
$ curl -i localhost:8080 -H 'Accept: application/stream+json'
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: application/stream+json;charset=UTF-8

HelloWorld
```

## Mono
- 1 또는 0 요소의 Publisher
- WebFlux의 요청 본문은 Mono로 감싸서 받아야 비동기 처리를 chain/compose 할 수 있다.
    - Mono로 감싸지 않고 받는 경우 논블러킹으로 동기화 처리된다.
    
    
```
curl -i localhost:8080/stream -d '{"value":1}{"value":2}{"value":3}' -H 'Content-Type: application/stream+json'  -H 'Accept: text/event-stream'
```