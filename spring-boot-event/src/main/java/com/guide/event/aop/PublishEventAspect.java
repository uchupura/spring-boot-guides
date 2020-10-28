package com.guide.event.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

/**
 * getConstructor() vs getDeclaredConstructor()
 * - getConstructor()의 경우 접근자가 public인 생성자에 접근 가능
 * - getDeclaredConstructor()의 경우 접근자에 상관없이 클래스 안의 ㄴ모든 생성자에 접근 가능
 */
@Slf4j
@Profile({"aop-async-event", "distributed-aop-async-event"})
@Component
@Aspect
public class PublishEventAspect implements ApplicationEventPublisherAware {

    private final String spelRegex = "^#\\{(.*)\\}$";
    private final Pattern spelPattern = Pattern.compile(spelRegex);

    private ApplicationEventPublisher eventPublisher;
    private ExpressionParser expressionParser = new SpelExpressionParser();

    @Pointcut("@annotation(publishEvent)")
    public void pointcut(PublishEvent publishEvent) {
    }

    /**
     * compile time에 체크하지 않음 주의!!!!!!!!
     *
     * @param publishEvent
     * @param retVal
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    @AfterReturning(pointcut = "pointcut(publishEvent)", returning = "retVal")
    public void afterReturning(PublishEvent publishEvent, Object retVal) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (isSpel(publishEvent.params())) {
            String spel = publishEvent.params().replaceAll(spelRegex, "$1");
            Object constructArg = expressionParser.parseExpression(spel).getValue(retVal);
            Object event = publishEvent.eventType()
                    .getConstructor(constructArg.getClass())
                    .newInstance(constructArg);

            eventPublisher.publishEvent(event);
        }
    }

    private boolean isSpel(String params) {
        if(StringUtils.isEmpty(params)) return false;
        return spelPattern.matcher(params).matches();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
