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
@CrossOrigin
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
        log.info(String.format("### Starting tab creation with externalId: %s. Name: %s", body.getExternalId(), body.getName()));
        Tab createdTab = tabsService.insert(body);
        log.info(createdTab.toString());
        URI location = URI.create(String.format("/%s/%s", "tabs", createdTab.getId()));
        log.info(String.format("### Tab created successfully. id: %s. externalId: %s. name: %s", createdTab.getId(), createdTab.getExternalId(), createdTab.getName()));
        return ResponseEntity.created(location).body(createdTab);
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
        log.info(String.format("Starting Deletion for tab id %s", id));
        tabsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
