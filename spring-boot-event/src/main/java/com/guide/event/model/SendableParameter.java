package com.guide.event.model;

import com.guide.event.model.email.EmailTemplateType;
import com.guide.event.model.sms.SmsTemplateType;
import lombok.Data;

@Data
public class SendableParameter {

    private static final EmailTemplateType emailTemplateType = EmailTemplateType.JOIN;
    private static final SmsTemplateType smsTemplateType = SmsTemplateType.JOIN;
    private String name;
    private String email;
    private String phoneNo;

    public static SendableParameter create(String name, String email, String phoneNo) {
        SendableParameter parameter = new SendableParameter();
        parameter.setName(name);
        parameter.setEmail(email);
        parameter.setPhoneNo(phoneNo);
        return parameter;
    }

    public EmailTemplateType getEmailTemplateType() {
        return emailTemplateType;
    }

    public SmsTemplateType getSmsTemplateType() {
        return smsTemplateType;
    }
}
