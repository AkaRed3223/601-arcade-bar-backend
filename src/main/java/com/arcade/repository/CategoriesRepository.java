package com.arcade.repository;

import com.arcade.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActive(Boolean isActive);
}
