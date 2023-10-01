package com.arcade.repository;

import com.arcade.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {
}
