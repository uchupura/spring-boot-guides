package com.guide.model;

import lombok.Getter;

@Getter
public class User implements IdAble {
    private Long id;
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
