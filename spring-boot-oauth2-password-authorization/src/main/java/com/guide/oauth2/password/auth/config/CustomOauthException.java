package com.guide.oauth2.password.auth.config;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class CustomOauthException extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }
}
