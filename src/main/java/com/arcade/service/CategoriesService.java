package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.CategoryNullException;
import com.arcade.exception.FailedToInactivateResourceException;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.exception.ResourceNotFoundException;
import com.arcade.model.Category;
import com.arcade.model.request.CategoryRequest;
import com.arcade.repository.CategoriesRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<Category> findAll() {
        return categoriesRepository.findAllByIsActive(true)
                .stream()
                .sorted(Comparator.comparingInt(Category::getPosition))
                .collect(Collectors.toList());
    }

    public Category findById(long id) {
        return categoriesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.CATEGORY, id));
    }

    public Category insert(CategoryRequest request) {
        int maxPosition = findAll().stream().mapToInt(Category::getPosition).max().orElse(0);

        if (StringUtils.isEmpty(request.getName())) {
            throw new CategoryNullException();
        }
        Category category = new Category(request.getName(), maxPosition + 1);

        try {
            categoriesRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(
                    ResourcesEnum.CATEGORY,
                    ResourcesFieldsEnum.NAME,
                    String.valueOf(request.getName())
            );
        }

        return category;
    }

    public void delete(Long id) {
        Category category = findById(id);
        category.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        category.setIsActive(false);

        try {
            categoriesRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new FailedToInactivateResourceException(ResourcesEnum.CATEGORY, ResourcesFieldsEnum.NAME, category.getName());
        }
    }
}
