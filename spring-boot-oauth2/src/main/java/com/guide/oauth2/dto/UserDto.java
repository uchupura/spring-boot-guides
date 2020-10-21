package com.guide.oauth2.dto;

import com.guide.oauth2.entity.user.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class UserDto {

    Long id;
    String loginId;
    String password;
    String name;
    String phone;
    String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    Long createdBy;
    Long lastModifiedBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;

    public void encryptPassword(String password) {
        this.password = password;
    }
}
