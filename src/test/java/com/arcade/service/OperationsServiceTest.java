package com.arcade.service;

import com.arcade.model.Operation;
import com.arcade.repository.OperationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationsServiceTest {

    @Mock
    OperationsRepository repository;

    @InjectMocks
    OperationsService service;

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new Operation()));

        Operation operation = service.findAll().get(0);

        assertNotNull(operation);
        assertNotNull(operation.getStartDate());
        //assertNull(operation.getEndDate());
        assertTrue(operation.getIsOpen());
        assertTrue(operation.getTabs().isEmpty());
    }

    @Test
    void findCurrent() {
    }

    @Test
    void initiateOperation() {
    }

    @Test
    void closeoutOperation() {
    }
}