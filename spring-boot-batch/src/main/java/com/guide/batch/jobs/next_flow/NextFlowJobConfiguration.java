//package com.guide.batch.jobs;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import static com.guide.batch.util.BatchUtil.StepName;
//
//@Slf4j                      // log 사용을 위한 lombok 어노테이션
//@RequiredArgsConstructor    // 생성자 DI를 위한 lombok 어노테이션
//@Configuration
//public class NextFlowJobConfiguration {
//
//    public static final String JOB_NAME = "BatchNextFlowJob";
//    private final JobBuilderFactory jobBuilderFactory;      // 생성자 DI 받음
//    private final StepBuilderFactory stepBuilderFactory;    // 생성자 DI 받음
//
//    @Bean
//    public Job nextFlowJob() {
//        return jobBuilderFactory.get(JOB_NAME)
//                .start(nextFlowStep1())
//                .next(nextFlowStep2())
//                .next(nextFlowStep3())
//                .build();
//    }
//
//    @Bean
//    public Step nextFlowStep1() {
//        return stepBuilderFactory.get(StepName(JOB_NAME, 1))
//                .tasklet((contribution, chunkContext) -> {
//                    log.info(">>>>> This is Step1");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    @Bean
//    public Step nextFlowStep2() {
//        return stepBuilderFactory.get(StepName(JOB_NAME, 2))
//                .tasklet((contribution, chunkContext) -> {
//                    log.info(">>>>> This is Step2");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//
//    @Bean
//    public Step nextFlowStep3() {
//        return stepBuilderFactory.get(StepName(JOB_NAME, 3))
//                .tasklet((contribution, chunkContext) -> {
//                    log.info(">>>>> This is Step3");
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }
//}
