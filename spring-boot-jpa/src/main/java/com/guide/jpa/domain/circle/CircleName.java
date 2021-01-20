package com.guide.jpa.domain.circle;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class CircleName {
    private String name;

    public CircleName(String value) {
        if(value == null) throw new IllegalArgumentException("서클명은 입력되어야 함");
        if(value.length() < 3) throw new IllegalArgumentException("서클명은 3글자 이상이어야 함(" + value + ")");
        if(value.length() > 20) throw new IllegalArgumentException("서클명은 20글자 이하이어야 함(" + value + ")");
        this.name = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(this.getClass() != obj.getClass()) return false;

        CircleName circleName = (CircleName)obj;
        return (name == circleName.getName());
    }
}
