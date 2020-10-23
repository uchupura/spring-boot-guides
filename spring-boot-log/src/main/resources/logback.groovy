import ch.qos.logback.classic.Level
import ch.qos.logback.classic.encoder.PatternLayoutEncoder

System.setProperty("java.net.preferIPv4Stack","true")

scan('5 seconds')
statusListener(OnConsoleStatusListener)

def profile = System.getProperty("spring.profiles.active","default").toUpperCase()
def hostName = hostname

def logMaxHistory = 5
def logLevel = Level.INFO
if ("PRD".equals(profile)) {
    localMaxHistory = 30
    logLevel = Level.INFO
}
println("######################################")
println("Profile : " + profile)
println("Host Name : " + hostName)
println("Log Level : " + logLevel)
println("Log Max History : " + logMaxHistory)
println("######################################")


appender("CONSOLE", ConsoleAppender) {

    encoder(PatternLayoutEncoder) {
        pattern = "%d |-${profile} [%thread] %5p %50.50c{50}:%4L - %.-16200m%n"
    }
}

appender("SERVER", RollingFileAppender) {
    Thread.sleep 300;
    file = "../logs/server.log"
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
}

appender("ERROR", RollingFileAppender) {
    Thread.sleep 300;
    file = "../logs/error.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%d ${hostName} |-${profile} [%thread] %5p %50.50c{50}:%4L - %.-16200m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "../logs/%d{yyyy-MM-dd}/error.log.%i.zip"
        maxHistory = logMaxHistory
        timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
            maxFileSize = "1gb"
        }
    }
    // 특정 레벨 이상만 로깅
    filter(ch.qos.logback.classic.filter.ThresholdFilter) {
        level = WARN // INFO 이상 레벨만 로깅
    }
}

logger("org.springframework", Level.ERROR)
logger("com.guide.log", Level.INFO)
//
//
//logger("ch.qos.logback.classic.gaffer.ConfigurationDelegate", Level.ERROR, ["CONSOLE"], false)
//logger("ch.qos.logback.classic.gaffer.ConfigurationDelegate", Level.ERROR)
//logger("org.apache.http.wire", Level.ERROR)
//logger("org.apache.http.red", Level.ERROR)
//logger("org.apache.http.headers", Level.ERROR)
//logger("org.apache.http.impl.conn.PoolingHttpClientConnectionManager", Level.ERROR)
//
//logger("org.hibernate", Level.ERROR)
//logger("org.hibernate.type.BasicTypeRegistry", Level.OFF)
//logger("org.hibernate.hql.internal.ast.QueryTranslatorImpl", Level.OFF)
//logger("org.hibernate.engine.internal.StatisticalLoggingSessionEventListener", Level.OFF)
//logger("org.hibernate.type.descriptor.converter.AttributeConverterTypeAdapter", Level.OFF)
//logger("org.hibernate.loader", Level.OFF)
//logger("org.hibernate.loader.plan.build.spi.LoadPlanTreePrinter", Level.OFF)
//logger("org.hibernate.loader.build.internal.spaces.QuerySpacesImpl", Level.OFF)
//logger("org.hibernate.persister.walking.spi.MetamodelGraphWalker", Level.OFF)
//
//logger("jdbc.connection", Level.ERROR)
//logger("org.hsqldb.jdbcDriver", Level.ERROR)

if ("PRD".equals(profile)) {
    root(logLevel, ["SERVER"])
} else {
    root(logLevel, ["CONSOLE","SERVER","ERROR"])
}

