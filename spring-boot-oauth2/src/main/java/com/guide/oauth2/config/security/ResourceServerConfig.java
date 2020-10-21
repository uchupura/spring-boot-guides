package com.guide.oauth2.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer       // OAuth 토큰에 의해 보호되고 있는 자원 서버 설정, 자원에 대한 보호를 구현 한 Spring Security Filter를 활성화
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()//allow h2 console access to admins only
                .anyRequest().authenticated();//all other urls can be access by any authenticated role

        http.csrf().disable();

        http.headers().frameOptions().disable();//allow use of frame to same origin urls
    }
}
