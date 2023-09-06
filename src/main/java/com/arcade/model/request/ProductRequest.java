package com.arcade.model.request;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Double price;
    private Long categoryId;

    public ProductRequest(String name, String price, Long categoryId) {
        this.name = name;
        this.price = Double.valueOf(price.replace(',', '.'));
        this.categoryId = categoryId;
    }
}
