package com.guide.oauth2.resource.controller;

import com.guide.oauth2.resource.common.BaseTest;
import com.guide.oauth2.resource.model.User;
import com.guide.oauth2.resource.security.WithMockOAuth2Client;
import com.guide.oauth2.resource.security.WithMockOAuth2User;
import com.guide.oauth2.resource.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.core.context.SecurityContextHolder.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTest {

    private final String RESOURCE = "User/";

    @Autowired
    private UserService userService;

    /*@BeforeAll
    public void setup() {
        List<String> roles = Arrays.asList("ROLE_MANAGER");
        List<SimpleGrantedAuthority> role = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        getContext().setAuthentication(new UsernamePasswordAuthenticationToken("username", "jang3827", role));
    }*/
    @Test
    public void getHello() throws Exception {
        this.mockMvc.perform(get("/v1/hello").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World!!")));
    }

    @Test
    @WithMockOAuth2User(
            user = @WithMockUser(
                    username = "uchupura",
                    authorities = {"ROLE_MANAGER"})
    )
    /*@WithMockOAuth2User(
            client = @WithMockOAuth2Client(
                    clientId = "testClientId",
                    scope = {"read", "write"},
                    authorities = {"ROLE_USER"}),
            user = @WithMockUser(
                    username = "uchupura",
                    authorities = {"ROLE_MANAGER"}))*/
    public void getUsers() throws Exception {
        /*MvcResult result = this.mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJ1c2VyX25hbWUiOiJ1Y2h1cHVyYSIsInNjb3BlIjpbInJlYWQiXSwiZXhwaXJlX2F0IjoxNjE1MjkyNTY5MTUyLCJleHAiOjE2MTUyOTI1NjksImF1dGhvcml0aWVzIjpbIlJPTEVfTUFOQUdFUiJdLCJqdGkiOiJkNjhmNjFjMC04M2ZlLTQ4OTUtOWJiYS0zMWU1ZjI3YTNiYTIiLCJjbGllbnRfaWQiOiJ0ZXN0Q2xpZW50SWQifQ._7ln1UTJtSpmcK2ePwuEfNL6TzLnWuBj1k6_ziPND_A"))
                .andExpect(status().isOk())
                .andReturn();*/
        MvcResult result = this.mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result);

        /*List<User> all = userService.findAll();
        System.out.println(all.size());*/
    }
}