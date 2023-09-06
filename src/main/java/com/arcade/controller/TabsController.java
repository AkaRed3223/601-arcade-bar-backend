package com.arcade.controller;

import com.arcade.model.Tab;
import com.arcade.model.request.TabRequest;
import com.arcade.service.TabsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tabs")
@AllArgsConstructor
@Slf4j
public class TabsController {

    private final TabsService tabsService;

    @GetMapping
    public ResponseEntity<List<Tab>> findAll() {
        List<Tab> response = tabsService.findAll();
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tab> findById(@PathVariable Long id) {
        Tab response = tabsService.findById(id);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Tab> insert(@RequestBody TabRequest body) {
        Tab insertedCategory = tabsService.insert(body);
        log.info(insertedCategory.toString());
        URI location = URI.create(String.format("/%s/%s", "tabs", insertedCategory.getId()));
        return ResponseEntity.created(location).body(insertedCategory);
    }

    @PutMapping("/{id}/insert")
    public ResponseEntity<Tab> insertProductInTab(@PathVariable Long id, @Param("productId") Long productId) {
        Tab response = tabsService.insertProductInTab(id, productId);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/remove")
    public ResponseEntity<Tab> deleteProductFromTab(@PathVariable Long id, @Param("productId") Long productId) {
        Tab response = tabsService.removeProductFromTab(id, productId);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<Tab> checkoutTab(@PathVariable Long id) {
        log.info(String.format("### Checking out Tab #%s", id));
        Tab response = tabsService.checkoutTab(id);
        log.info("### Checkout successful");
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tabsService.delete(id);
        log.info(String.format("### Deletion success: id %s", id));
        return ResponseEntity.noContent().build();
    }
}
