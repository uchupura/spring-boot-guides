package com.guide.jpa.계층관계;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryDTO {
    private String id;
    private String name;
//    private CategoryDTO parent;
    private List<CategoryDTO> children;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        /*if(category.getParent() != null)
            this.parent = new CategoryDTO(category.getParent());*/
        if(category.getChildren() != null) {
            this.children = category.getChildren().stream().map(o -> new CategoryDTO(o)).collect(Collectors.toList());
        }

    }
}
