package com.guide.oauth2.resource.config;

import com.guide.oauth2.resource.service.CertificateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private CertificateInterceptor certificateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(certificationInterceptor).excludePathPatterns("/v1/sessions", "/v1/health", "/favicon.ico");
        registry.addInterceptor(certificateInterceptor);
    }
}
