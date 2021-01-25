package com.guide.quartz.controller;

import com.guide.quartz.dto.QuizDTO;
import com.guide.quartz.job.RewardJob;
import com.guide.quartz.service.QuartzService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.guide.quartz.service.QuartzService.buildCronExpression;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(QuizAPI.URL)
public class QuizAPI {
    public static final String URL = "/v1/quiz";

    private final QuartzService quartzService;

    @PostMapping
    public void createQuiz(@RequestBody QuizDTO.Request.Create body) throws SchedulerException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("quizNo", body.getQuizNo());
        params.put("episodeNo", body.getEpisodeNo());
//        log.info(buildCronExpression(body.getEndTime()));
        quartzService.addCronJob(RewardJob.class, "RewardJob:"+body.getQuizNo(), "RewardJob:"+body.getQuizNo(), params, buildCronExpression(body.getEndTime()));
    }
}