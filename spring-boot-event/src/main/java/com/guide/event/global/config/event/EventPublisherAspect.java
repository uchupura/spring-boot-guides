package com.guide.event.global.config.event;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EventPublisherAspect implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    private final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.publisher = eventPublisher;
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional) || @annotation(com.guide.event.global.config.event.PublishEvent)")
    public Object handleEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean nested;
        Boolean appliedValue = threadLocal.get();

        if (appliedValue != null && appliedValue) {
            nested = true;
        } else {
            nested = false;
            threadLocal.set(Boolean.TRUE);
        }

        if (!nested) Event.set(publisher);

        try {
            return joinPoint.proceed();
        } finally {
            if (!nested) {
                Event.unset();
                threadLocal.remove();
            }
        }
    }
}
