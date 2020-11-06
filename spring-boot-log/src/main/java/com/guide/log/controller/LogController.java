package com.guide.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
public class LogController {

    @Autowired
    private Environment env;

    @GetMapping("t")
    public void trace() {

        log.trace("TRACE Logging");
    }

    @GetMapping("d")
    public void debug() {
        log.debug("DEBUG Logging");
    }

    @GetMapping("i")
    public String info() {
        String profile = Arrays.asList(env.getActiveProfiles()).size() > 0 ? Arrays.asList(env.getActiveProfiles()).get(0) : "default";
        log.info("INFO Logging");
        return profile;
    }

    @GetMapping("w")
    public void warn() {
        log.warn("WARN Logging");
    }

    @GetMapping("e")
    public void error() {
        log.error("ERROR Logging");
    }
}
