package com.guide.jpa.식별관계.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Child {
    @EmbeddedId
    private ChildId childId = new ChildId();

    @MapsId("test")
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Builder
    private Child(Parent parent) {
        this.parent = parent;
    }
    /**
     * 생성 메서드
     */
    public static Child createChild(Parent parent) {
        Child child = Child.builder()
                .parent(parent)
                .build();
        child.getChildId().setChildId(2l);
        return child;
    }
}