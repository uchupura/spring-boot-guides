package com.guide.jpa.계층관계;

import com.guide.jpa.global.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.guide.jpa.계층관계.QCategory.category;

@Repository
public class CategoryQueryRepository extends Querydsl4RepositorySupport {
    public CategoryQueryRepository() {
        super(Category.class);
    }

    public List<Category> findAllCategory() {
        return select(category).distinct()
                .from(category)
                .join(category.children).fetchJoin()
                .where(category.parent.isNull())
                .fetch();
    }
    public Category findCategory(String id) {
        return select(category)
                .from(category)
                .join(category.children).fetchJoin()
                .where(category.id.eq(id))
                .fetchOne();
    }
}
