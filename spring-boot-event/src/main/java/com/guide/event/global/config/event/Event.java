package com.guide.event.global.config.event;

import org.springframework.context.ApplicationEventPublisher;

public class Event {

    private static ThreadLocal<ApplicationEventPublisher> threadLocal = new ThreadLocal<>();

    public static void publish(Object event) {
        if(event == null) return;

        if (threadLocal.get() != null) {
            threadLocal.get().publishEvent(event);
        }
    }

    public static void set(ApplicationEventPublisher publisher) {
        threadLocal.set(publisher);
    }

    public static void unset() {
        threadLocal.remove();
    }
}
