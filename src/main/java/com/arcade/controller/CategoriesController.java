package com.arcade.controller;

import com.arcade.model.Category;
import com.arcade.model.request.CategoryRequest;
import com.arcade.service.CategoriesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Slf4j
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> response = categoriesService.findAll();
        response.sort(Comparator.comparingInt(Category::getPosition));
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable long id) {
        return ResponseEntity.ok(categoriesService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryRequest body) {
        Category insertedCategory = categoriesService.insert(body);
        URI location = URI.create(String.format("/%s/%s", "categories", insertedCategory.getId()));
        return ResponseEntity.created(location).body(insertedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoriesService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
