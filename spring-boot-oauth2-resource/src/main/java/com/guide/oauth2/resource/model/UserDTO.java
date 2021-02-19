package com.guide.oauth2.resource.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {
    public static class Request {
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @Getter
        public static class User {
            private String uid;
            private String password;
            private String name;
            private Role role;
        }
    }
    public static class Response {
        @Getter
        public static class User {
            private Long id;
            private String uid;
            private String password;
            private String name;
            private Role role;

            public User(Long id, String uid, String password, String name, Role role) {
                this.id = id;
                this.uid = uid;
                this.password = password;
                this.name = name;
                this.role = role;
            }
        }
    }
}
