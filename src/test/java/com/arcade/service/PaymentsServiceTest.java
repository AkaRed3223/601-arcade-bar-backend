package com.arcade.service;

import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.model.Payment;
import com.arcade.model.Tab;
import com.arcade.model.request.PaymentRequest;
import com.arcade.repository.PaymentsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceTest {

    @Mock
    PaymentsRepository paymentsRepository;

    @InjectMocks
    PaymentsService paymentsService;

    @Test
    @DisplayName("Insert Payment - Return a Payment object when payment is inserted")
    void insert1() {
        Tab tab = new Tab(1L, "Cesar", "111111111", 1L);
        PaymentRequest paymentRequest = new PaymentRequest(5.0, "Cesar", "Auth 123456");

        Payment response = assertDoesNotThrow(() -> paymentsService.insert(tab, paymentRequest));

        assertNotNull(response);
        assertEquals(1L, response.getTabExternalId());
        assertEquals(5.0, response.getValue());
        assertEquals("Cesar", response.getName());
        assertEquals("Auth 123456", response.getDetails());
        assertNotNull(response.getTime());
    }

    @Test
    @DisplayName("Insert Payment - Should throw ResourceAlreadyExistsException when the same payment is inserted")
    void insert2() {
        Tab tab = new Tab(1L, "Cesar", "111111111", 1L);
        PaymentRequest paymentRequest = new PaymentRequest(5.0, "Cesar", "Auth 123456");

        when(paymentsRepository.save(any(Payment.class))).thenThrow(DataIntegrityViolationException.class);

        ResourceAlreadyExistsException exception = assertThrows(
                ResourceAlreadyExistsException.class,
                () -> paymentsService.insert(tab, paymentRequest)
        );

        assertNotNull(exception);
        assertEquals("Resource 'PAYMENT' with field 'VALUE-5.0' already exists", exception.getMessage());
    }
}