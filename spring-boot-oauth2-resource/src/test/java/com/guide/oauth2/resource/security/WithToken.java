package com.guide.oauth2.resource.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WithToken {

    String FAKE_BEARER_TOKEN = "fake.bearer.token";

    String value() default FAKE_BEARER_TOKEN;
}
