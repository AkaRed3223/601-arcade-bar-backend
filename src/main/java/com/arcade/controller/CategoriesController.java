package com.arcade.controller;

import com.arcade.model.Category;
import com.arcade.model.request.CategoryRequest;
import com.arcade.service.CategoriesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Slf4j
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {

        List<Category> categories = categoriesService.findAll();

        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryRequest body) {
        Category insertedCategory  = categoriesService.insert(body);
        URI location = URI.create(String.format("/%s/%s", "categories", insertedCategory.getId()));
        return ResponseEntity.created(location).body(insertedCategory);
    }

}
