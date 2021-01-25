package com.guide.quartz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

public class QuizDTO {
    public static class Request {
        @ToString
        @Getter
        @NoArgsConstructor
        public static class Create {
            private Long quizNo;
            private Long episodeNo;
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime startTime;
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime endTime;

            public Create(Long quizNo, Long episodeNo, LocalDateTime startTime, LocalDateTime endTime) {
                this.quizNo = quizNo;
                this.episodeNo = episodeNo;
                this.startTime = startTime;
                this.endTime = endTime;
            }
        }
    }
    public static class Response {

    }
}

