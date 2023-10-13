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
@CrossOrigin
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        log.info("### Starting fetching all Categories");
        List<Category> response = categoriesService.findAll();
        if (CollectionUtils.isEmpty(response)) {
            log.info("### No categories have been found");
        } else {
            log.info(response.toString());
            log.info("### Fetching Categories Success");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryRequest body) {
        log.info(String.format("### Starting inserting Categories with NAME-%s", body.getName()));
        Category insertedCategory = categoriesService.insert(body);
        log.info(insertedCategory.toString());
        log.info("### Inserting Categories Success");
        URI location = URI.create(String.format("/categories/%s", insertedCategory.getId()));
        return ResponseEntity.created(location).body(insertedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(String.format("### Starting Deletion for Category ID-%s", id));
        categoriesService.delete(id);
        log.info("### Category Deletion success");
        return ResponseEntity.noContent().build();
    }
}