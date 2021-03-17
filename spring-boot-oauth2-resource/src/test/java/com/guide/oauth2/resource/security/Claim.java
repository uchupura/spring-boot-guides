package com.guide.oauth2.resource.security;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(AttachClaims.class)
public @interface Claim {

    String name();

    String value();

    Class<?> type() default String.class;
}

