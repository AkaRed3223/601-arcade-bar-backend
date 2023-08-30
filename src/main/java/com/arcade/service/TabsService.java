package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.ResourceAlreadyExistsException;
import com.arcade.exception.ResourceNotFoundException;
import com.arcade.model.Product;
import com.arcade.model.Tab;
import com.arcade.model.request.TabRequest;
import com.arcade.repository.TabsRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TabsService {

    private final TabsRepository tabsRepository;
    private final ProductsService productsService;

    public List<Tab> findAll() {
        return tabsRepository.findAll();
    }

    public Tab findByExternalId(Long externalId) {
        return tabsRepository
                .findByExternalId(externalId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.TAB, externalId));
    }

    public Tab insert(TabRequest request) {

        List<Product> products = request.getProducts().stream().map(productsService::findByName).collect(Collectors.toList());

        Tab tab = new Tab(request.getExternalId(), request.getName(), products);

        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, String.valueOf(request.getExternalId()));
        }

        return tab;
    }

    public Tab findByName(String name) {
        return tabsRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.TAB, name));
    }

    public Tab findById(Long id) {
        return tabsRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.TAB, id));
    }

    /*public Product update(Long id, ProductRequest request) {

        Product product = findById(id);

        if (StringUtils.isNotBlank(request.getName())) product.setName(request.getName());
        if (request.getPrice() != null) product.setPrice(request.getPrice());

        try {
            productsRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.PRODUCT, ResourcesFieldsEnum.NAME, String.valueOf(request.getName()));
        }

        return product;
    }*/

    /*public void delete(Long id) {
        findById(id);
        productsRepository.deleteById(id);
    }*/

}
