package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.exception.ResourceNotFoundException;
import com.arcade.model.Category;
import com.arcade.model.Product;
import com.arcade.model.request.ProductRequest;
import com.arcade.repository.ProductsRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesService categoriesService;

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public Product findById(Long id) {
        return productsRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.PRODUCT, id));
    }

    public Product findByName(String name) {
        return productsRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.PRODUCT, name));
    }

    public Product insert(ProductRequest request) {

        Category category = categoriesService.findByName(request.getCategory().getName());
        Product product = new Product(request.getName(), request.getPrice(), category);

        try {
            productsRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.PRODUCT, ResourcesFieldsEnum.NAME, String.valueOf(request.getName()));
        }

        return product;
    }

    public Product update(Long id, ProductRequest request) {

        Product product = findById(id);

        if (StringUtils.isNotBlank(request.getName())) product.setName(request.getName());
        if (request.getPrice() != null) product.setPrice(request.getPrice());

        try {
            productsRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.PRODUCT, ResourcesFieldsEnum.NAME, String.valueOf(request.getName()));
        }

        return product;
    }

    public void delete(Long id) {
        findById(id);
        productsRepository.deleteById(id);
    }

}
