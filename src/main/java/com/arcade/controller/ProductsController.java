package com.arcade.controller;

import com.arcade.model.Product;
import com.arcade.model.request.ProductRequest;
import com.arcade.service.ProductsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Slf4j
@CrossOrigin
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@Param("isActive") Boolean isActive) {
        if (isActive == null) isActive = true;
        var response = productsService.findAllByIsActive(isActive);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(String.format("### Starting Deletion for Product ID-%s", id));
        productsService.delete(id);
        log.info(String.format("### Deletion success: id %s", id));
        return ResponseEntity.noContent().build();
    }

}
