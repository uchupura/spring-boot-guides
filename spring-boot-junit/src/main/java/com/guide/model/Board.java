package com.guide.model;

import lombok.Getter;

@Getter
public class Board implements IdAble {
    private Long id;
    private String title;
    private String content;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
