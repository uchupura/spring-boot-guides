package com.guide.jpa.식별관계.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class ChildId implements Serializable {
    private Long test;
    @Setter
    private Long childId;

    public ChildId(Long test, Long childId) {
        this.test = test;
        this.childId = childId;
    }
}
