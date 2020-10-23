package com.guide.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

    @GetMapping("t")
    public void trace() {
        log.trace("TRACE Logging");
    }

    @GetMapping("d")
    public void debug() {
        log.debug("DEBUG Logging");
    }

    @GetMapping("i")
    public void info() {
        log.info("INFO Logging");
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
