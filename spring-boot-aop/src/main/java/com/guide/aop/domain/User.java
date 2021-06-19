package com.guide.aop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long idx;

    private String email;

    private String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
