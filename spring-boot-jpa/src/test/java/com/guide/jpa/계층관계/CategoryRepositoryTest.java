package com.guide.jpa.계층관계;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryQueryRepository categoryQueryRepository;

    @Test
    public void 카테고리_생성() {
        Category category1 = new Category("고객정보", "고객정보", null);
        categoryRepository.save(category1);

        Category category2 = new Category("고객명", "고객정보", category1);
        categoryRepository.save(category2);

        Category category2_1 = new Category("고객명2", "고객정보", category1);
        categoryRepository.save(category2_1);

        Category category3 = new Category("신청함", "고객정보", category2);
        categoryRepository.save(category3);

        Category category4 = new Category("요금조회", "요금조회", null);
        categoryRepository.save(category4);

        Category category5 = new Category("납부주기", "요금조회", category4);
        categoryRepository.save(category5);

        Category category5_1 = new Category("납부주기2", "요금조회", category4);
        categoryRepository.save(category5_1);

        Category category6 = new Category("해지함", "요금조회", category5);
        categoryRepository.save(category6);

        em.flush();
        em.clear();

        List<Category> categories = categoryRepository.findByParentIsNull();
        List<CategoryDTO> categoryDTOS = categories.stream().map(o -> new CategoryDTO(o)).collect(Collectors.toList());
        assertEquals(2, categories.size());
        assertEquals(2, categories.get(0).getChildren().size());
        assertEquals(1, categoryDTOS.get(0).getChildren().get(0).getChildren().size());

        Category category = categoryQueryRepository.findCategory("요금조회");
        assertEquals(2, category.getChildren().size());
    }
}
