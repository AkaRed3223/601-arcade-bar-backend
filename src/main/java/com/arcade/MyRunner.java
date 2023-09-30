/*
package com.arcade;

import com.arcade.model.request.CategoryRequest;
import com.arcade.model.request.ProductRequest;
import com.arcade.model.request.TabRequest;
import com.arcade.service.CategoriesService;
import com.arcade.service.OperationsService;
import com.arcade.service.ProductsService;
import com.arcade.service.TabsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MyRunner implements CommandLineRunner {

    private CategoriesService categoriesService;
    private ProductsService productsService;
    private TabsService tabsService;
    private OperationsService operationsService;

    @Override
    public void run(String... args) {
        categoriesService.insert(new CategoryRequest("Lanches", null));
        categoriesService.insert(new CategoryRequest("Bebidas", null));
        categoriesService.insert(new CategoryRequest("Porções", null));

        productsService.insert(new ProductRequest("X-Burger", "24.90", 1L));
        productsService.insert(new ProductRequest("X-Bacon", "26.90", 1L));
        productsService.insert(new ProductRequest("X-Pagan", "26.90", 1L));
        productsService.insert(new ProductRequest("Cerveja Original", "12.90", 2L));
        productsService.insert(new ProductRequest("Cerveja Heineken", "14.90", 2L));
        productsService.insert(new ProductRequest("Coca-Cola", "6.90", 2L));
        productsService.insert(new ProductRequest("Batata frita", "14.90", 3L));

        operationsService.initiateOperation();

        tabsService.insert(new TabRequest("Cesar", "11988989117", 64L));
        tabsService.insert(new TabRequest("Mi", "11983604792", 65L));
    }
}
*/
