package com.guide.openfeign.global.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;

public class FeignContractConfiguration {
    @Bean
    public Contract contract() {
        return new Contract.Default();
    }
}
