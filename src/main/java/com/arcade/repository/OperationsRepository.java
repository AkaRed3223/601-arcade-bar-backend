package com.arcade.repository;

import com.arcade.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationsRepository extends JpaRepository<Operation, Long> {
    Optional<Operation> findByIsOpenTrue();
}
