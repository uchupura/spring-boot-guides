package com.guide.oauth2.password.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OAuthToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}
