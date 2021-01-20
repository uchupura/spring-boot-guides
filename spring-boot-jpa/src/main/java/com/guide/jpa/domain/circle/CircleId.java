package com.guide.jpa.domain.circle;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Embeddable
public class CircleId implements Serializable {
    private String id;

    public CircleId(String value) {
        if(value == null) throw new IllegalArgumentException();
        this.id = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(this.getClass() != obj.getClass()) return false;

        CircleId circleId = (CircleId)obj;
        return (id == circleId.getId());
    }
}
