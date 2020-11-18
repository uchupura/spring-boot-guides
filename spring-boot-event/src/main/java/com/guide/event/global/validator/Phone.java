package com.guide.event.global.validator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD}) // 필드에 적용
@Retention(RetentionPolicy.RUNTIME) // 실행할동안 어노테이션 유지
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    String message() default "휴대폰 번호"; // 오류 메세지 default
    Class[] groups() default {};
    Class[] payload() default {};
}
