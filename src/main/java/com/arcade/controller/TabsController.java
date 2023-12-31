package com.arcade.controller;

import com.arcade.model.Payment;
import com.arcade.model.Tab;
import com.arcade.model.request.PaymentRequest;
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
        List<Tab> response = tabsService.findAllFromCurrentOperation();
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
        log.info(String.format("### Starting tab creation with externalId: %s. Name: %s. Phone: %s", body.getExternalId(), body.getName(), body.getPhone()));
        Tab createdTab = tabsService.insert(body);
        log.info(createdTab.toString());
        URI location = URI.create(String.format("/%s/%s", "tabs", createdTab.getId()));
        log.info(String.format("### Tab created successfully. id: %s. externalId: %s. name: %s", createdTab.getId(), createdTab.getExternalId(), createdTab.getName()));
        return ResponseEntity.created(location).body(createdTab);
    }

    @PutMapping("/{id}/insert")
    public ResponseEntity<Tab> insertProductInTab(
            @PathVariable("id") Long tabId,
            @Param("productId") Long productId) {

        log.info(String.format("### Start inserting PRODUCT-%s in TAB-%s", productId, tabId));
        Tab response = tabsService.insertProductInTab(tabId, productId);
        log.info(response.toString());
        log.info("### Product insertion in tab success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/remove")
    public ResponseEntity<Tab> deleteProductFromTab(
            @PathVariable("id") Long tabId,
            @Param("productId") Long productId) {
        log.info(String.format("### Start removing PRODUCT-%s in TAB-%s", productId, tabId));
        Tab response = tabsService.removeProductFromTab(tabId, productId);
        log.info(response.toString());
        log.info("### Product deletion from tab success");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Payment> payTab(@PathVariable Long id, @RequestBody PaymentRequest body) {
        log.info(String.format("### Starting payment for Tab: #%s. Name: %s. Value: %s", id, body.getName(), body.getName()));
        Payment response = tabsService.payTab(id, body);
        log.info(response.toString());
        log.info("### Payment successful");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<Tab> checkoutTab(@PathVariable Long id) {
        log.info(String.format("### Checking out Tab #%s", id));
        Tab response = tabsService.checkoutTab(id);
        log.info(response.toString());
        log.info("### Checkout successful");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(String.format("Starting Deletion for tab id %s", id));
        tabsService.delete(id);
        log.info(String.format("Deletion success for TAB id: %s", id));
        return ResponseEntity.noContent().build();
    }
}
