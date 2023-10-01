package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.model.Payment;
import com.arcade.model.Tab;
import com.arcade.model.request.PaymentRequest;
import com.arcade.repository.PaymentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;

    public List<Payment> findAll() {
        return paymentsRepository.findAll();
    }

    public Payment insert(Tab tab, PaymentRequest request) {
        Payment payment = new Payment(tab, request);

        try {
            paymentsRepository.save(payment);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(
                    ResourcesEnum.PAYMENT,
                    ResourcesFieldsEnum.VALUE,
                    String.valueOf(request.getValue())
            );
        }

        return payment;
    }

}
