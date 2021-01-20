package com.guide.jpa.domain.product;

import com.guide.jpa.domain.category.CategoryId;
import com.guide.jpa.global.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.guide.jpa.domain.product.QProduct.product;

@Repository
public class ProductQueryRepository extends Querydsl4RepositorySupport {
    public ProductQueryRepository() {
        super(Product.class);
    }

    public Product findById(ProductId id) {
        return select(product)
                .from(product)
                .where(product.id.eq(id))
                .fetchOne();
    }
    public List<Product> findByCategoryId(CategoryId id) {
        return select(product)
                .from(product)
                .where(product.categoryIds.contains(id))
                .fetch();
    }
}
