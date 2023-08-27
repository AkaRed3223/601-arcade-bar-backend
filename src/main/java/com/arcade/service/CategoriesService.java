package com.arcade.service;

import com.arcade.model.Category;
import com.arcade.model.request.CategoryRequest;
import com.arcade.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public Category insert(CategoryRequest request) {

        Category category = new Category(request.getName(), request.getPosition());

        categoriesRepository.save(category);

        return category;
    }

    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

}
