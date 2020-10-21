package com.guide.oauth2.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static org.springframework.util.ObjectUtils.isEmpty;

@Aspect
@Component
@Slf4j
public class ExceptionAspect{

    @AfterThrowing(pointcut="execution(* com..*Controller.*(..))", throwing="error")
    public void logAfterThrowing(JoinPoint thisJoinPoint, Throwable error) throws Exception {

        StringBuffer buf = new StringBuffer();

        Object[] args = thisJoinPoint.getArgs();

        buf.append("\n**** Exception Aspect executed Controller ***********************************************");
        buf.append("\n* " + thisJoinPoint.getTarget().getClass().getName()	+ "." + thisJoinPoint.getSignature().getName() + "()");

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if(args[i] != null) buf.append("\n* " + (i + 1) + " arg's value : " + args[i].toString());
            }
            if(!isEmpty(error.getStackTrace())) {
                buf.append("\n* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> STACK TRACE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                for (int j = 0; j < error.getStackTrace().length; j++) {
                    buf.append("\n* " + error.getStackTrace()[j].toString());
                }
            }
            buf.append("\n******************************************************************************\n");
        } else {
            buf.append("\n**** No arguments ************************************************************\n");
        }
        log.error(buf.toString());
        error.printStackTrace();
    }
}
