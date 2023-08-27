package com.arcade.repository;

import com.arcade.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
