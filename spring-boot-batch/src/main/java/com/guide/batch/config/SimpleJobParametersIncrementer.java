package com.guide.batch.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class SimpleJobParametersIncrementer implements JobParametersIncrementer {

    private String key;
    public final String RUN_ID_KEY = "run.id";

    public SimpleJobParametersIncrementer() {
        this.key = RUN_ID_KEY;
    }

    public SimpleJobParametersIncrementer(String key) {
        this.key = key;
    }

    @Override
    public JobParameters getNext(JobParameters parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return new JobParametersBuilder().addLong(key, 1L).toJobParameters();
        }

        long id = parameters.getLong(key, 1L) + 1;
        return (new JobParametersBuilder(parameters)).addLong(key, id).toJobParameters();
    }
}
