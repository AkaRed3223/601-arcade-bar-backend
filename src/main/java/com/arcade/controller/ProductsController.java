package com.arcade.controller;

import com.arcade.model.Product;
import com.arcade.model.request.ProductRequest;
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

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        var response = productsService.findAll();
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        Product response = productsService.findById(id);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductRequest body) {
        Product insertedProduct = productsService.insert(body);
        log.info(insertedProduct.toString());
        URI location = URI.create(String.format("/%s/%s", "products", insertedProduct.getId()));
        return ResponseEntity.created(location).body(insertedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product response = productsService.update(id, request);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productsService.delete(id);
        log.info(String.format("### Deletion success: id %s", id));
        return ResponseEntity.noContent().build();
    }

}
