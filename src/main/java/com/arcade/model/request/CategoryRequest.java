package com.arcade.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequest {

    private String name;
    private int position;
}
