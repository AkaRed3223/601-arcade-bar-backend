package com.arcade.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TabRequest {

    private String name;
    private Long externalId;
    private List<String> products;
}
