## 날짜 타입 JSON 변환
### Request Parameter
#### GET 요청 시 Query 파라미터를 받는 방법
* @ModelAttribute로 DTO 객체로 받는다.
* @RequestParameter로 필드로 받는다.

> @ModelAttribute를 지정하지 않아도 별도로 어노테이션 지정이 없으면 @ModelAttribute가 자동 할당된다.

#### @ModelAttribute : 2020-10-02T01:00:00 형식으로 전달 필요
```java
@ToString
@Getter
@Setter
@NoArgsConstructor
public class GetModel {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime requestDateTime;
}
```

#### @RequestParameter : 2020-10-02T01:00:00 형식으로 전달 필요
```java
public class FileController {
    
    @GetMapping("/requestParameter")
    public String requestParameter(
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
            @RequestParam("requestDateTime") LocalDateTime requestDateTime) {
    
        log.info("requestParameter 요청 데이터 = {}", requestDateTime);
    
        return "requestParameter 성공";
    }
}
```


### Request Body
#### 직렬화 순서
* 두 어노테이션 모두가 있으면 @JsonFormat이 진행된다
* @JsonFormat이 틀리면 @DateTimeFormat이 맞더라도 직렬화는 실패한다
* 단, @DateTimeFormat이 있다면 @DateTimeFormat의 포맷으로 직렬화가 진행된다.

#### @ReqeustBody : startDate(2020-10-01), endDate(2020-10-02T01:00:00)
```java
public class DownloadFileRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
}
```

### @ResponseBody
#### 직렬화
* @JsonFormat만 가능
