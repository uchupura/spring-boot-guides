package com.guide.aop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    private Long idx;

    private String title;

    private String content;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
