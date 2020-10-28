package com.guide.event.service.email;

import com.guide.event.model.email.EmailTemplateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    public void sendEmail(String email, EmailTemplateType type) {
        log.info("send {} email to {}", type, email);
    }
}
