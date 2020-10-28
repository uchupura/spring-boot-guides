package com.guide.event.service.sms;

import com.guide.event.model.sms.SmsTemplateType;

public interface SmsService {

    void sendSms(String phoneNo, SmsTemplateType type);
}
