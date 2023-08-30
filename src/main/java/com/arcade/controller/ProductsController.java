package com.arcade.controller;

import com.arcade.model.Product;
import com.arcade.model.request.ProductRequest;
import com.arcade.service.CategoriesService;
import com.arcade.service.ProductsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Slf4j
public class ProductsController {

    private final ProductsService productsService;
    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        var response = productsService.findAll();
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        return ResponseEntity.ok(productsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductRequest body) {

        Product insertedProduct = productsService.insert(body);
        URI location = URI.create(String.format("/%s/%s", "categories", insertedProduct.getId()));
        return ResponseEntity.created(location).body(insertedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productsService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
