package com.guide.oauth2.password.auth.config;

import com.guide.oauth2.password.auth.config.support.Cryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        String encrypt = Cryptor.encrypt(rawPassword.toString());
        return encrypt;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println(Cryptor.encrypt("jang3827!"));
        // testSecret L83hw5VqohtfwfjEWSXXlA==
        // jang3827! 4Je8J8vRH6cjDB8fwBymqw==
        return encodedPassword.equals(Cryptor.encrypt(rawPassword.toString()));
    }
}
