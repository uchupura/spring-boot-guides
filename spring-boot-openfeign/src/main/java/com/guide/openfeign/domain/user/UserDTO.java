package com.guide.openfeign.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    public static class Request {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            String loginId;
            String password;
            String name;
            String phone;
            String email;
            String role;
            LocalDateTime createdDate;

            @Builder
            public Create(String loginId, String password, String name, String phone, String email, String role) {
                this.loginId = loginId;
                this.password = password;
                this.name = name;
                this.phone = phone;
                this.email = email;
                this.role = role;
                this.createdDate = LocalDateTime.now();
            }
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Filter {
            String loginId;
            LocalDateTime createdDate;

            @Builder
            public Filter(String loginId, LocalDateTime createdDate) {
                this.loginId = loginId;
                this.createdDate = createdDate;
            }
        }
    }

    public static class Response {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            Long id;
            String loginId;
            String password;
            String name;
            String phone;
            String email;
            String role;
            LocalDateTime createdDate;

            @Builder
            public Create(Long id, String loginId, String password, String name, String phone, String email, String role, LocalDateTime createdDate) {
                this.id = id;
                this.loginId = loginId;
                this.password = password;
                this.name = name;
                this.phone = phone;
                this.email = email;
                this.role = role;
                this.createdDate = createdDate;
            }
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class User {
            Long id;
            String loginId;
            String password;
            String name;
            String phone;
            String email;
            String role;
            LocalDateTime createdDate;

            @Builder
            public User(Long id, String loginId, String password, String name, String phone, String email, String role, LocalDateTime createdDate) {
                this.id = id;
                this.loginId = loginId;
                this.password = password;
                this.name = name;
                this.phone = phone;
                this.email = email;
                this.role = role;
                this.createdDate = createdDate;
            }
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Users {
            List<User> users = new ArrayList<>();
        }
    }
}