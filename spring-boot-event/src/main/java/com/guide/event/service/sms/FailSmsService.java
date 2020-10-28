package com.guide.event.service.sms;

import com.guide.event.model.sms.SmsTemplateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("fail")
@Slf4j
@Service
public class FailSmsService implements SmsService {

    public void sendSms(String phoneNo, SmsTemplateType type) {
        log.info("send {} sms to {}", type, phoneNo);
        throw new RuntimeException("sms fail");
    }
}
