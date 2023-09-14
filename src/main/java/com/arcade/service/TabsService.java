package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.*;
import com.arcade.model.Product;
import com.arcade.model.Tab;
import com.arcade.model.request.TabRequest;
import com.arcade.repository.TabsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TabsService {

    private final TabsRepository tabsRepository;
    private final ProductsService productsService;

    public List<Tab> findAll() {
        return tabsRepository.findAll();
    }

    public Tab findById(Long id) {
        return tabsRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.TAB, id));
    }

    public Tab insert(TabRequest request) {
        var openTab = tabsRepository.findByExternalIdAndIsOpen(request.getExternalId(), true);
        if (openTab.isPresent()) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, String.valueOf(request.getExternalId()));
        }

        Tab tab = new Tab(request.getExternalId(), request.getName());

        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, String.valueOf(request.getExternalId()));
        }

        return tab;
    }

    public Tab insertProductInTab(Long id, Long productId) {
        return updateProductsFromTab(id, productId, true);
    }

    public Tab removeProductFromTab(Long id, Long productId) {
        return updateProductsFromTab(id, productId, false);
    }

    private Tab updateProductsFromTab(Long id, Long productId, Boolean isAdd) {
        Tab tab = findById(id);

        if (!tab.getIsOpen()) {
            throw new TabAlreadyClosedException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, tab.getExternalId().toString());
        }

        if (isAdd) {
            tab.getProducts().add(productsService.findById(productId));
        } else {
            Product product = tab.getProducts().stream().filter(p -> p.getId().equals(productId)).findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.PRODUCT, productId));

            tab.getProducts().remove(product);
        }

        tab.setTotal(tab.getProducts().stream().mapToDouble(Product::getPrice).sum());

        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, String.valueOf(tab.getExternalId()));
        }
        return tab;

    }

    public Tab checkoutTab(Long id) {
        Tab tab = findById(id);

        if (!tab.getIsOpen()) {
            log.warn(String.format("### Error checking out Tab #%s", tab.getExternalId()));
            throw new TabAlreadyClosedException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, tab.getExternalId().toString());
        }

        tab.setIsOpen(false);
        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new FailedToCheckoutException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, String.valueOf(tab.getExternalId()));
        }
        return tab;
    }

    public void delete(Long id) {
        Tab tab = findById(id);
        if (!CollectionUtils.isEmpty(tab.getProducts())) {
            throw new TabNotEmptyException();
        }
        try {
            tabsRepository.deleteById(tab.getId());
        } catch (Exception e) {
            log.error(String.format("Failed to delete TAB id: %s, externalId: %s, name: %s", tab.getId(), tab.getExternalId(), tab.getName()));
            throw new ResourceNotFoundException(ResourcesEnum.TAB, tab.getName());
        }
    }


}
