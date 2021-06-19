package com.guide.jpa.OneToMany_단방향.dto;

import com.guide.jpa.OneToMany_단방향.entity.Item;
import lombok.Getter;

@Getter
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemDto(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
