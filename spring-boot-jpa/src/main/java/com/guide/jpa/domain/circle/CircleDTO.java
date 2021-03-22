package com.guide.jpa.domain.circle;

import com.guide.jpa.domain.user.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

public class CircleDTO {
    public static class Request {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            String userId;
            String name;
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Join {
            String userId;
            String circleId;
        }


    }

    public static class Response {
        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            String circleId;
            String circleName;

            public Create(com.guide.jpa.domain.circle.Circle circle) {
                this.circleId = circle.getId().getId();
                this.circleName = circle.getName().getName();
            }
        }

        @Getter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Circle {
            String circleId;
            String circleName;
            String ownerName;
            List<UserDTO.Response.User> members;

            public Circle(com.guide.jpa.domain.circle.Circle circle) {
                this.circleId = circle.getId().getId();
                this.circleName = circle.getName().getName();
                this.ownerName = circle.getOwner().getName().getValue();
                members = circle.getMembers().stream()
                        .map(user -> new UserDTO.Response.User(user))
                        .collect(Collectors.toList());
            }
        }
    }
}
