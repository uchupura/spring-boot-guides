package com.guide.redis.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter @Setter
@RedisHash("token")
public class Token {

    @Id
    private String id;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String scope;

    @Builder
    public Token(String id, String accessToken, String refreshToken, Long expiresIn, String scope) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }
}
