package com.guide.jpa.domain.product;

import com.guide.jpa.domain.category.Category;
import com.guide.jpa.domain.category.CategoryId;
import com.guide.jpa.domain.category.CategoryRepository;
import com.guide.jpa.domain.common.model.Money;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Rollback(false)
@Transactional
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class ProductTest {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Autowired
    private ProductQueryRepository productQueryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Order(1)
    public void 카테고리_등록() {
        CategoryId categoryId = new CategoryId(1l);
        Category category = new Category(categoryId, "가전");
        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isEqualTo(category.getId());
    }

    @Test
    @Order(2)
    public void 상품_등록() {
        Product product = new Product(new ProductId("prod-999"), "999", new Money(999), "detail",
                Arrays.asList(new ExternalImage("externalPath", "uchupura"),
                        new InternalImage("internal1", true),
                        new InternalImage("internal2", false)));
        product.getCategoryIds().add(new CategoryId(1l));
        productRepository.save(product);
    }

    @Test
    @Order(3)
    public void 특정_카테고리에_존재하는_상품리스트_검색() {
        List<Product> products = productQueryRepository.findByCategoryId(new CategoryId(1l));
        products.forEach(product -> System.out.println(product.getName()));
    }

    @Test
    @Order(4)
    public void 특정_상품_검색() {
        Product product = productQueryRepository.findById(new ProductId("PRD001"));
        System.out.println(product.getName());
    }

    /*@Test
    @Order(5)
    public void 상품_삭제() {
        productRepository.deleteById(new ProductId("PRD001"));
    }*/
}