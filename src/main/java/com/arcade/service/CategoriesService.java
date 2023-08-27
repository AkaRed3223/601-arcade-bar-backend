package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.exception.ResourceNotFoundException;
import com.arcade.model.Category;
import com.arcade.model.request.CategoryRequest;
import com.arcade.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<Category> findAll() {
        return categoriesRepository.findAll();
    }

    public Category findById(long id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.CATEGORY, id));
    }

    public Category insert(CategoryRequest request) {
        Category category = new Category(
                request.getName(),
                request.getPosition()
        );

        try {
            categoriesRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.CATEGORY, ResourcesFieldsEnum.POSITION, String.valueOf(request.getPosition()));
        }

        return category;
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = findById(id);

        Optional.of(request.getName()).ifPresent(category::setName);
        Optional.of(request.getPosition()).ifPresent(category::setPosition);

        categoriesRepository.save(category);

        return category;
    }

    public void delete(Long id) {
        findById(id);
        categoriesRepository.deleteById(id);
    }

}
