package com.guide.batch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Role {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private Long id;
    private Long memberId;
    private String role;
}
