package com.guide.batch.config;

import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

    @Bean(name="jobParametersIncrementer")
    public JobParametersIncrementer jobParametersIncrementer() {
        return new SimpleJobParametersIncrementer();
    }
}
