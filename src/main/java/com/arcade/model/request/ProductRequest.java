package com.arcade.model.request;

import com.arcade.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private Double price;
    private Category category;
}
