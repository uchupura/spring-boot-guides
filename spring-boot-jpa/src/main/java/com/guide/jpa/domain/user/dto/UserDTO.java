package com.guide.jpa.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDTO {

    public static class Request {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            private String name;
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Update {
            String id;
            String name;
        }
    }

    public static class Response {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            String id;
            String name;

            public Create(String id, String name) {
                this.id = id;
                this.name = name;
            }
        }

        @Getter @Setter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class User {
            String id;
            String name;

            // 도메인 객체를 인자로 받음
            public User(com.guide.jpa.domain.user.entity.User user) {
                this.id = user.getId().getValue();
                this.name = user.getName().getValue();
            }
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Update {
            String id;
            String name;

            public Update(com.guide.jpa.domain.user.entity.User user) {
                this.id = user.getId().getValue();
                this.name = user.getName().getValue();
            }
        }
    }
}
