package com.guide.restdocs.controller;

import com.guide.restdocs.common.BaseTest;
import com.guide.restdocs.domain.User;
import com.guide.restdocs.domain.User.CreateUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTest {

    private final String RESOURCE = "User/";

    public static FieldDescriptor[] getResponseFields(ConstrainedFields fields) {
        return new FieldDescriptor[]{
                fields.withPath("users").type(JsonFieldType.ARRAY).description("유저 리스트"),
                fields.withRequiredPath("users[].loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                fields.withPath("users[].password").type(JsonFieldType.STRING).description("로그인 패스워드"),
                fields.withPath("users[].name").type(JsonFieldType.STRING).description("이름"),
                fields.withPath("users[].phone").type(JsonFieldType.STRING).description("폰").optional(),
                fields.withPath("users[].email").type(JsonFieldType.STRING).description("이메일")
        };
    }

    @Test
    void test01_createUser() throws Exception {

        System.out.println("createUser()");
        ConstrainedFields fields = new ConstrainedFields(CreateUser.class);

        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .loginId("member1")
                .password("member1")
                .name("멤버1")
                //.phone("000000")
                .email("member1@gmail.com")
                .build());
        users.add(User.builder()
                .loginId("member2")
                .password("member2")
                .name("멤버2")
                .phone("01022222222")
                .email("member2@gmail.com")
                .build());
        CreateUser createUser = new CreateUser(users);


        this.mockMvc
                .perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createUser)))
                .andExpect(status().isOk())
                .andDo(document(RESOURCE + "{method-name}",
                        requestFields(getResponseFields(fields)),
                        responseFields(getResponseFields(fields))
                ));
    }

    @Test
    public void test03() {
        System.out.println("test03()");
    }

    @Test
    public void test02() {
        System.out.println("test02()");
    }
}