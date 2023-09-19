package com.arcade.service;

import com.arcade.exception.OperationNotReadyForCloseoutException;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.exception.ResourceNotFoundException;
import com.arcade.model.Operation;
import com.arcade.repository.OperationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OperationsService {

    private final OperationsRepository operationsRepository;

    public List<Operation> findAll() {
        return operationsRepository.findAll();
    }

    public Operation findCurrent() {
        return operationsRepository
                .findByIsOpenTrue()
                .orElseThrow(() -> new ResourceNotFoundException("There is no open operation"));
    }

    public Operation initiateOperation() {
        if (operationsRepository.findByIsOpenTrue().isPresent()) {
            throw new ResourceAlreadyExistsException("There is an operation in progress");
        }

        Operation newOperation = new Operation();
        try {
            operationsRepository.save(newOperation);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Failed to start a new operation");
        }
        return newOperation;
    }

    public Operation closeoutOperation() {
        Operation operation = findCurrent();
        operation.getTabs().forEach(tab -> {
            if (tab.getIsOpen()) throw new OperationNotReadyForCloseoutException();
        });
        operation.closeOperation();

        try {
            operationsRepository.save(operation);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("Failed to Closeout");
        }

        return operation;
    }
}
