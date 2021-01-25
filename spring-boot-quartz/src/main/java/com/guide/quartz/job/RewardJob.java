package com.guide.quartz.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@DisallowConcurrentExecution
public class RewardJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Long quizNo = (Long)context.getJobDetail().getJobDataMap().get("quizNo");
        Long episodeNo = (Long)context.getJobDetail().getJobDataMap().get("episodeNo");
        log.info(">>> fire {}", LocalDateTime.now().toString());
        log.info("quizNo : {}", quizNo);
        log.info("episodeNo : {}", episodeNo);
    }
}
