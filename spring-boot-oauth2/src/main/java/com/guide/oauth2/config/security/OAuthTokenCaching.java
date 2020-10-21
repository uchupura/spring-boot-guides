package com.guide.oauth2.config.security;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class OAuthTokenCaching {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(* org.springframework.security.oauth2.provider.client.JdbcClientDetailsService.loadClientByClientId(..))")
    public void loadClientByClientId() {
    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.TokenStore.readAuthentication(..))")
    public void readAuthentication() {
    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.TokenStore.storeAccessToken(..))")
    public void storeAccessToken() {
    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.TokenStore.readAccessToken(..))")
    public void readAccessToken() {
    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.TokenStore.removeAccessToken(..))")
    public void removeAccessToken() {
    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.TokenStore.removeRefreshToken(..))")
    public void removeRefreshToken() {
    }

    @Pointcut("execution(* org.springframework.security.oauth2.provider.token.TokenStore.removeAccessTokenUsingRefreshToken(..))")
    public void removeAccessTokenUsingRefreshToken() {
    }

    private <T> T getOrProgress(ProceedingJoinPoint pjp, String key, Class<T> returnType, long expireSecond) throws Throwable {
        T value;
        ValueOperations operations = redisTemplate.opsForValue();
        Object cacheValue = operations.get(key);
        if (cacheValue == null) {
            System.out.println(returnType.getName() + " not cached");
            value = returnType.cast(pjp.proceed());
            if (value != null) {
                if (expireSecond > 0) {
                    operations.set(key, value, expireSecond, TimeUnit.SECONDS);
                } else {
                    operations.set(key, value);
                }
            }
        } else {
            System.out.println(returnType.getName() + " cached");
            value = returnType.cast(cacheValue);
        }

        return value;
    }

    @Around("loadClientByClientId() || readAuthentication() || readAccessToken()")
    public Object readHandler(ProceedingJoinPoint pjp) throws Throwable {
        // …(생략)
        if (pjp.proceed() instanceof BaseClientDetails) {
            System.out.println(">>>>> JdbcClientDetailsService");
            return getOrProgress(pjp, ((BaseClientDetails)pjp.proceed()).getClientId(), ClientDetails.class, ((BaseClientDetails)pjp.proceed()).getAccessTokenValiditySeconds());
        } else if (pjp.proceed() instanceof DefaultOAuth2AccessToken) {
            System.out.println("##### DefaultOAuth2AccessToken");
            return getOrProgress(pjp, ((DefaultOAuth2AccessToken)pjp.proceed()).getValue(), OAuth2AccessToken.class, 0);
        } else if (pjp.proceed() instanceof OAuth2Authentication) {
            System.out.println("$$$$$ OAuth2Authentication");
            return getOrProgress(pjp, ((OAuth2Authentication)pjp.proceed()).getName(), OAuth2Authentication.class, 0);
        }

        return pjp.proceed();
    }

    @Before("removeAccessToken()")
    public void removeAccessTokenHandler(JoinPoint pjp) {
        // delete cache
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken)Arrays.stream(pjp.getArgs())
                .filter(arg -> arg instanceof DefaultOAuth2AccessToken)
                .findFirst()
                .orElse(null);
        if (token != null) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>> removeAccessToken() : " + token.getValue());
            redisTemplate.delete(token.getValue());
        }
    }

    @Before("removeRefreshToken() || removeAccessTokenUsingRefreshToken()")
    public void removeRefreshHandler(JoinPoint pjp) {
        // delete cache
        System.out.println(">>>>>>>>>>>>>>>>>>>>> removeRefreshToken(), removeAccessTokenUsingRefreshToken()");
    }

    @Before("storeAccessToken()")
    public void storeHandler(JoinPoint pjp) {
        // set cache
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken)Arrays.stream(pjp.getArgs())
                .filter(arg -> arg instanceof DefaultOAuth2AccessToken)
                .findFirst()
                .orElse(null);
        if (token != null) {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(token.getValue(), token);
            System.out.println(">>>>>>>>>>>>>>>>>>>>> storeAccessToken() " + token.getValue());
        }
    }
}
