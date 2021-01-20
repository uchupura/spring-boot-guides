package com.guide.jpa.domain.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void 카테고리_등록() {
        CategoryId categoryId = new CategoryId(1l);
        Category category = new Category(categoryId, "가전");
        categoryRepository.save(category);
    }
}