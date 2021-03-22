package com.guide.jpa.domain.circle;

import com.guide.jpa.global.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.guide.jpa.domain.circle.QCircle.circle;
import static com.guide.jpa.domain.user.entity.QUser.user;

@Repository
public class CircleQueryRepository extends Querydsl4RepositorySupport {
    public CircleQueryRepository() {
        super(Circle.class);
    }

    public List<Circle> findAllCircles() {
        System.out.println();
        return select(circle)
                .from(circle)
                .join(circle.owner, user).fetchJoin()
                .fetch();
    }
}
