package com.arcade.service;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;
import com.arcade.exception.*;
import com.arcade.model.Operation;
import com.arcade.model.Payment;
import com.arcade.model.Product;
import com.arcade.model.Tab;
import com.arcade.model.request.PaymentRequest;
import com.arcade.model.request.TabRequest;
import com.arcade.repository.TabsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TabsService {

    private final TabsRepository tabsRepository;
    private final ProductsService productsService;
    private final OperationsService operationsService;
    private final PaymentsService paymentsService;

    public List<Tab> findAll() {
        //return tabsRepository.findAll();
        return tabsRepository.findAllByIsDeletedFalse();
    }

    public List<Tab> findAllFromCurrentOperation() {
        Operation currentOperation = operationsService.findCurrent();
        List<Tab> tabs = findAll();

        return tabs.stream()
                .filter(t -> t.getOperationId().equals(currentOperation.getId()))
                .collect(Collectors.toList());
    }

    public Tab findById(Long id) {
        return tabsRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.TAB, id));
    }

    @Transactional
    public Tab insert(TabRequest request) {
        tabsRepository.findByExternalIdAndIsOpen(request.getExternalId(), true)
                .ifPresent(tab -> {
                    throw new ResourceAlreadyExistsException(
                            ResourcesEnum.TAB,
                            ResourcesFieldsEnum.EXTERNAL_ID,
                            request.getExternalId().toString()
                    );
                });

        Operation operation = operationsService.findCurrent();
        Tab tab = new Tab(
                request.getExternalId(),
                request.getName(),
                request.getPhone(),
                operation.getId()
        );

        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(
                    ResourcesEnum.TAB,
                    ResourcesFieldsEnum.EXTERNAL_ID,
                    String.valueOf(request.getExternalId())
            );
        }

        operation.getTabs().add(tab);
        operationsService.insert(operation);

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

        Product productToAdd = productsService.findById(productId);
        if (isAdd) {
            tab.getProducts().add(productToAdd);
        } else {
            Product product = tab.getProducts().stream().filter(p -> p.getId().equals(productId)).findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(ResourcesEnum.PRODUCT, productId));

            tab.getProducts().remove(product);
        }

        tab.setTotalDue(Double.sum(tab.getTotalDue(), productToAdd.getPrice()));

        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, String.valueOf(tab.getExternalId()));
        }
        return tab;

    }

    public Payment payTab(Long id, PaymentRequest body) {
        Tab tab = findById(id);

        if (!tab.getIsOpen()) {
            log.warn(String.format("### Error inserting payment for Tab #%s", tab.getExternalId()));
            throw new TabAlreadyClosedException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, tab.getExternalId().toString());
        }

        double value = body.getValue();
        double totalDue = tab.getTotalDue();

        if (value >= totalDue) {
            value = totalDue;
            body.setValue(value);
        }

        Payment payment = paymentsService.insert(tab, body);

        tab.getPayments().add(payment);
        tab.setTotalPaid(tab.getTotalPaid() + value);
        tab.setTotalDue(Math.max(0.0, totalDue - value));

        try {
            tabsRepository.save(tab);
        } catch (DataIntegrityViolationException e) {
            throw new FailedToPayException(tab.getId(), value);
        }
        return payment;
    }

    public Tab checkoutTab(Long id) {
        Tab tab = findById(id);

        if (!tab.getIsOpen()) {
            log.warn(String.format("### Error checking out Tab #%s", tab.getExternalId()));
            throw new TabAlreadyClosedException(ResourcesEnum.TAB, ResourcesFieldsEnum.EXTERNAL_ID, tab.getExternalId().toString());
        }

        payTab(tab.getId(), new PaymentRequest(tab.getTotalDue(), tab.getName(), "CHECKOUT"));

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
        tab.setIsDeleted(true);
        tab.setIsOpen(false);
        tabsRepository.save(tab);
        /*try {
            tabsRepository.deleteById(tab.getId());
        } catch (Exception e) {
            log.error(String.format("Failed to delete TAB id: %s, externalId: %s, name: %s", tab.getId(), tab.getExternalId(), tab.getName()));
            throw new ResourceNotFoundException(ResourcesEnum.TAB, tab.getName());
        }*/
    }


}
