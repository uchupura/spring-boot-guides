package com.guide.oauth2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringOauth2AuthorizationApp implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(SpringOauth2AuthorizationApp.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(passwordEncoder.encode("testSecret"));
    }
}
