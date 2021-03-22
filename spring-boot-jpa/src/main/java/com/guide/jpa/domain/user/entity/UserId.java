package com.guide.jpa.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Embeddable
public class UserId implements Serializable {
    @Column(name = "user_id")
    private String value;

    public UserId(String value) {
        if(StringUtils.isEmpty(value)) throw new IllegalArgumentException("value가 null이거나 빈 문자열임");
        this.value = value;
    }
}

