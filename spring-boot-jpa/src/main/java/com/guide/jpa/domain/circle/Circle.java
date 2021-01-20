package com.guide.jpa.domain.circle;

import com.guide.jpa.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter @Setter
@Entity
public class Circle {

    @EmbeddedId
    CircleId id;

    @Embedded
    CircleName name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User owner;

    @OneToMany(mappedBy = "circle")
    List<User> members = new ArrayList<>();

    private Circle(CircleId id, CircleName name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
//        this.owner.setOwnerCircle(this);
    }

    public static Circle create(CircleName name, User owner) {
        CircleId circleId = new CircleId(UUID.randomUUID().toString());
        Circle circle = new Circle(circleId, name, owner);
        return circle;
    }

    private void setOwner(User user){
        this.owner = user;
        // object references an unsaved transient instance - save the transient instance before flushing
        // 개체가 저장되지 않은 일시적인 인스턴스를 참조합니다.
        // => User 객체가 저장되지 않은 Circle 객체를 참조해서 발생됩니다.
        // => cascade = CascadeType.ALL을 통해서 Circle 개체가 저장된 후 참조하도록 수정
        //this.owner.setOwnerCircle(this);
        // 결론? cascade = CascadeType.ALL 사용을 위해서는 자식 객체에 부모 객체를 지정해줘야 함
    }
}
