package com.guide.jpa.domain.user;

import com.guide.jpa.domain.circle.Circle;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.*;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
public class User {

    @EmbeddedId
    UserId id;

    @Embedded
    UserName name;

    /*@OneToOne(mappedBy = "owner")
    Circle ownerCircle;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circle_id")
    Circle circle;

    // 인스턴스를 처음 생성할 때 사용한다.
    public User(UserName name) {
        if(name == null) throw new IllegalArgumentException();
        id = new UserId(UUID.randomUUID().toString());
        this.name = name;
    }

    public void changeName(UserName name) {
        if(name == null) throw new IllegalArgumentException();
        this.name = name;
    }

    public void join(Circle circle) {
        this.circle = circle;
    }

//    public void setOwnerCircle(Circle circle) {
//        this.ownerCircle = circle;
//    }
}
