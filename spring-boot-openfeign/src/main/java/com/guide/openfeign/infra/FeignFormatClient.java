package com.guide.openfeign.infra;

import com.guide.openfeign.global.config.FeignFormatConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(value = "format-client", path = "/search", configuration = {FeignFormatConfiguration.class})
public interface FeignFormatClient {
    @GetMapping
    String search(@RequestParam("startDate") LocalDate from, @RequestParam("endDate") LocalDate end);
}
