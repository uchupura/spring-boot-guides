# LogBack
## logback.groovy 적용 절차
### build.gradle 파일에 필요한 dependency를 추가
```
dependencies {
    implementation 'org.codehaus.groovy:groovy'
    implementation 'org.codehaus.janino:janino'
}
```
### resources 폴더 하위에 logback.groovy 파일을 생성 후 로깅을 설정
```
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

appender("CONSOLE", ConsoleAppender) {

    encoder(PatternLayoutEncoder) {
        pattern = "%d |-${profile} [%thread] %5p %50.50c{50}:%4L - %.-16200m%n"
    }
}

appender("SERVER", RollingFileAppender) {
    Thread.sleep 300;
    file = "./logs/server.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%d ${hostName} |-${profile} [%thread] %5p %50.50c{50}:%4L - %.-16200m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "../logs/%d{yyyy-MM-dd}/server.log.%i.zip"
        maxHistory = logMaxHistory
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "1gb"
        }
    }
    // 특정 레벨만 로깅
    filter(com.guide.log.config.CustomFilter) {
        levelList = [DEBUG, INFO]
    }
}

root(Level.DEBUG, ["CONSOLE",SERVER"])
```

## LogBack 적용 우선 순위
* 특정 패키지에 직접 레벨을 설정한 것이 root에 설정 된 레벨보다 우선 시 된다.
    * root의 레벨은 DEBUG이고, com.guide.log 패키지 하위 레벨은 INFO인 상황에서 com.guide.log 패키지 하위 클래스에서 DEBUG 레벨로 로깅을 시도 시 로깅 대상에서 제외된다.
```
// 특정 패키지에 직접 레벨 설정
logger("com.guide.log", INFO)
// root 레벨 설정
root(DEBUG, ["SERVER","ERROR"])
```
