package com.guide.jpa.domain.product;

import com.guide.jpa.domain.category.CategoryId;
import com.guide.jpa.domain.common.model.Money;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Product {
    @EmbeddedId
    private ProductId id;

    /**
     * 단방향 M:N 연관을 ID 참조 방식으로 구현한 것
     * @ElementCollection을 사용했기 때문에 Product 삭제 시 매핑에 사용된 조인 데이터도 같이 삭제된다.
     */
    @ElementCollection
    @CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
    private Set<CategoryId> categoryIds = new HashSet<>();

    private String name;

    private Money price;

    private String detail;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @OrderColumn(name = "list_idx")
    private List<Image> images = new ArrayList<>();


    public Product(ProductId id, String name, Money price, String detail, List<Image> images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.images.addAll(images);
    }
}