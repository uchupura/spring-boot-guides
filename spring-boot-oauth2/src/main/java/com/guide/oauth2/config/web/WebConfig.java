package com.guide.oauth2.config.web;

import com.guide.oauth2.config.resolver.PostPageableArgumentResolver;
import com.guide.oauth2.config.resolver.RequestBodyWithPageableArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PostPageableArgumentResolver postPageableArgumentResolver;

    private final RequestBodyWithPageableArgumentResolver requestBodyWithPageableArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(postPageableArgumentResolver);
        argumentResolvers.add(requestBodyWithPageableArgumentResolver);
    }
}
