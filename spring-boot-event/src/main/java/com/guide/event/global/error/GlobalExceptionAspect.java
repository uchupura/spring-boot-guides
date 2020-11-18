package com.guide.event.global.error;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class GlobalExceptionAspect {

    @AfterThrowing(pointcut="execution(* com..*API.*(..))", throwing="error")
    public void printException(JoinPoint thisJoinPoint, Throwable error) {
        StringBuffer buf = new StringBuffer();

        buf.append("\n**** Exception Aspect executed ***********************************************");
        buf.append("\n* " + thisJoinPoint.getTarget().getClass().getName()	+ "." + thisJoinPoint.getSignature().getName() + "()");
        buf.append(printArgsToString(thisJoinPoint.getArgs()));
        buf.append(printStackTraceToString(error));
        buf.append("\n******************************************************************************\n");

        log.error(buf.toString());
    }

    private String printArgsToString(Object[] args) {
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) sb.append("\n* " + (i + 1) + " arg's value : " + args[i].toString());
            }
        }
        return sb.toString();
    }
    private String printStackTraceToString(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(throwable.toString());
        sb.append("\n");
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        for (int idx = 0; idx < stackTrace.length; idx++) {
            sb.append("\tat ");
            sb.append(stackTrace[idx].toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
