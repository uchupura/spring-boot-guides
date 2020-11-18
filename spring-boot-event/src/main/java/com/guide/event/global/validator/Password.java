package com.guide.event.global.validator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})    // 필드에 적용
@Retention(RetentionPolicy.RUNTIME)                 // 실행 할 동안 어노테이션 유지
@Constraint(validatedBy = PasswordValidator.class)  // ConstraintValidator interface 구현을 나타내는 어노테이션
public @interface Password {
    String message() default "패스워드";       // ValidationMessages.properties에서 특정 property key를 가리키는 메시지 (제약 조건 위배 시 메시지로 사용)
    Class[] groups() default {};            // 유효성 검사가 어떤 상황에서 실행되는지 정의 할 수 있는 매개 변수 그룹
    Class[] payload() default {};           // 유효성 검사에 전달 될 payload를 정의할 수 있는 매개 변수
}
