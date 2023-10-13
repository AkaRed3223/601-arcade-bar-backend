package com.arcade.repository;

import com.arcade.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIsActive(Boolean isActive);
}