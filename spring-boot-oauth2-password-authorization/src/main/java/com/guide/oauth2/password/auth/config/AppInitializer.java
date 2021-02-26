package com.guide.oauth2.password.auth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppInitializer
{
    @Bean
    public FilterRegistrationBean<AccessTokenFilter> sessionTimeoutFilter()
    {
        FilterRegistrationBean<AccessTokenFilter> registrationBean = new FilterRegistrationBean<>();
        AccessTokenFilter filter = new AccessTokenFilter();

        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/oauth/token");
        registrationBean.setOrder(1); // set precedence
        return registrationBean;
    }
}
