package com.guide.event.domain.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guide.event.domain.model.Address;
import com.guide.event.global.validator.Password;
import com.guide.event.global.validator.Phone;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class MemberDTO {
    interface OnCreate{}
    interface OnUpdate{}

    public static class Request {
        @Getter
        @NoArgsConstructor
        public static class Create {
            @Null(groups = OnCreate.class)
            @NotNull(groups = OnUpdate.class)
            private Long id;
            private String name;
            @Phone
            private String phone;
            @Email
            private String email;
            @Password
            private String password;
            private Address address;

            @Builder
            public Create(String name, String phone, String email, String password, Address address) {
                this.name = name;
                this.phone = phone;
                this.email = email;
                this.password = password;
                this.address = address;
            }
        }
    }

    public static class Response {
        @Getter @Setter
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Create {
            private Long id;
            private String name;
            private String phone;
            private String email;
            private String password;
            private Address address;
        }
    }

}