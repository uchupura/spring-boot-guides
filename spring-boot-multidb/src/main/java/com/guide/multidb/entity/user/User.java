package com.guide.multidb.entity.user;

import com.guide.multidb.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter @Setter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public void update(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

