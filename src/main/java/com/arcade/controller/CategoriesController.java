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
        Category response = categoriesService.findById(id);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryRequest body) {
        Category insertedCategory = categoriesService.insert(body);
        log.info(insertedCategory.toString());
        URI location = URI.create(String.format("/%s/%s", "categories", insertedCategory.getId()));
        return ResponseEntity.created(location).body(insertedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        Category response = categoriesService.update(id, request);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriesService.delete(id);
        log.info(String.format("### Deletion success: id %s", id));
        return ResponseEntity.noContent().build();
    }

}
