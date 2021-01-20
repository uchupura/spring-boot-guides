package com.guide.jpa.domain.category;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category {
    @EmbeddedId
    CategoryId id;

    String name;

    public Category(CategoryId id, String name) {
        this.id = id;
        this.name = name;
    }
}
