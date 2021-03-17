package com.guide.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
public class DownloadFileRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
}
