package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.FailedToInactivateResourceException;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.exception.ResourceNotFoundException;
import com.arcade.model.Category;
import com.arcade.model.Product;
import com.arcade.model.request.ProductRequest;
import com.arcade.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesService categoriesService;

    public List<Product> findAllByIsActive(Boolean isActive) {
        return productsRepository.findAllByIsActive(isActive);
    }

    public Product findById(Long id) {
        return productsRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.PRODUCT, id));
    }

    public Product insert(ProductRequest request) {
        Category category = categoriesService.findById(request.getCategoryId());
        Product product = new Product(request.getName(), request.getPrice(), category);

        try {
            productsRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.PRODUCT, ResourcesFieldsEnum.NAME, String.valueOf(request.getName()));
        }

        return product;
    }

    public void delete(Long id) {
        Product product = findById(id);
        product.setIsActive(false);
        product.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        productsRepository.save(product);

        try {
            productsRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new FailedToInactivateResourceException(ResourcesEnum.PRODUCT, ResourcesFieldsEnum.ID, product.getId().toString());
        }
    }
}
