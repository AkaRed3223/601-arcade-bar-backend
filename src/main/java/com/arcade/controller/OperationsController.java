package com.arcade.controller;

import com.arcade.model.Operation;
import com.arcade.service.OperationsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/operations")
@Slf4j
@AllArgsConstructor
public class OperationsController {

    private final OperationsService operationsService;

    @GetMapping
    public ResponseEntity<List<Operation>> fetchAll() {
        log.info("### Starting fetching all Operations");
        var response = operationsService.findAll();
        if (CollectionUtils.isEmpty(response)) {
            log.info("### No operations have been found");
        } else {
            log.info(response.toString());
            log.info("### Fetching Operations Success");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<Operation> fetchCurrent() {
        log.info("### Starting fetching current Operation");
        var response = operationsService.findCurrent();
        log.info("### Success fetching current Operation");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/initiate")
    public ResponseEntity<Operation> initiateOperation() {
        Operation startedOperation = operationsService.initiateOperation();
        log.info(startedOperation.toString());
        URI location = URI.create(String.format("/%s/%s", "operations", startedOperation.getId()));
        return ResponseEntity.created(location).body(startedOperation);
    }

    @PostMapping("/closeout")
    public ResponseEntity<Operation> closeoutOperation() {
        Operation closedOperation = operationsService.closeoutOperation();
        log.info(closedOperation.toString());
        URI location = URI.create(String.format("/%s/%s", "operations", closedOperation.getId()));
        return ResponseEntity.created(location).body(closedOperation);
    }
}