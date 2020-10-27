package com.guide.batch.jobs.custom_db_chunk_writer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@JobScope
@Component
public class CustomJobParameter {

    @Value("#{jobParameters[status]}")
    private String status;

    @Value("#{jobParameters[version]}")
    private String version;
}
