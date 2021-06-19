package com.guide.jpa.OneToMany_단방향.repository;

import com.guide.jpa.OneToMany_단방향.entity.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.guide.jpa.OneToMany_단방향.entity.QItem.item;

@RequiredArgsConstructor
@Repository
public class ItemQueryRepository {
    private final EntityManager em;

    public Item findItem(Long id) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(item)
                .from(item)
                .where(item.id.eq(id))
                .fetchOne();
    }
}
