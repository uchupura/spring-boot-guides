package com.guide.jpa.domain.user.entity;

import com.guide.jpa.domain.user.support.StringCryptoConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class UserName {

    @Convert(converter = StringCryptoConverter.class)
    @Column(name = "name")
    private String value;

    public UserName(String value) {
        if(StringUtils.isEmpty(value)) throw new IllegalArgumentException("value가 null이거나 빈 문자열임");
        if(value.length() < 3) throw new IllegalArgumentException("사용자명은 3글자 이상이어야 함(" + value + ")");
        if(value.length() > 20) throw new IllegalArgumentException("사용자명은 20글자 이하이어야 함(" + value + ")");
        this.value = value;
    }
}
