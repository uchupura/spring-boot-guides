package com.guide.oauth2.controller;

import com.guide.oauth2.dto.BaseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(TokenController.URI)
public class TokenController {

    public static final String URI = "/api/tokens";

    @Resource(name="tokenServices")
    ConsumerTokenServices tokenServices;

    @Resource(name="tokenStore")
    TokenStore tokenStore;

    @GetMapping("/{clientId}")
    public BaseDto<String> getTokens(@PathVariable String clientId) {

        List<String> tokenValues = new ArrayList<String>();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId(clientId);
        if (tokens!=null){
            for (OAuth2AccessToken token:tokens){
                tokenValues.add(token.getValue());
            }
        }

        return new BaseDto(tokenValues.size(), tokenValues);
    }

    @DeleteMapping
    public BaseDto<String> revokeToken(HttpServletRequest request) {

        List<String> tokenValues = new ArrayList<String>();
        String authorization = request.getHeader("Authorization");
        String[] tokens = authorization.split(" ");
        tokenServices.revokeToken(tokens[1]);
        tokenValues.add(tokens[1]);

        return new BaseDto(tokenValues.size(), tokenValues);
    }
}
