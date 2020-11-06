package com.guide.restdocs.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Getter
public class User implements Serializable {

    String loginId;
    String password;
    String name;
    String phone;
    String email;

    @Builder
    public User(String loginId, String password, String name, String phone, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    @Getter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class CreateUser implements Serializable {
        private List<User> users;

        public CreateUser(List<User> users) {
            this.users = users;
        }
    }
}