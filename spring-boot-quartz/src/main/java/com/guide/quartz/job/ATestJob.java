package com.guide.quartz.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class ATestJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        log.info("ATestJob Running");
        try {
            String id = context.getJobDetail().getKey().getName();
            Integer count = (Integer)context.getJobDetail().getJobDataMap().get("count");
            log.info("ID is " + count);
            context.getJobDetail().getJobDataMap().put("count", count + 1);
//            testService.run(id);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
