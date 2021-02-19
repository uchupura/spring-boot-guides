package com.guide.event.domain.user;

import com.guide.event.global.config.event.Event;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends AbstractAggregateRoot {
    @Builder
    public User(String uid, String password, String name, Role role) {
        this.uid = uid;
        this.password = password;
        this.name = name;
        this.role = role;
        Event.publish(new UserCreatedEvent(this));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void changePassword(String password) {
        this.password = password;
        Event.publish(new UserPasswordChangedEvent(this));
    }
}
