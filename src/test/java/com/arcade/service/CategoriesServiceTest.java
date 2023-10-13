package com.arcade.service;

import com.arcade.exception.CategoryNullException;
import com.arcade.model.Category;
import com.arcade.model.request.CategoryRequest;
import com.arcade.repository.CategoriesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {

    @Mock
    CategoriesRepository categoriesRepository;

    @InjectMocks
    CategoriesService categoriesService;

    @Test
    void findAll() {
        when(categoriesRepository.findAllByIsActive(true)).thenReturn(List.of(
                new Category("Lanches", 1),
                new Category("Bebidas", 2),
                new Category("Porções", 3),
                new Category("Fichas", 4)
        ));

        List<Category> response = categoriesService.findAll();

        assertEquals(1, response.get(0).getPosition());
        assertEquals(2, response.get(1).getPosition());
        assertEquals(3, response.get(2).getPosition());
        assertEquals(4, response.get(3).getPosition());
    }

    @Test
    void findById() {
        Category expectedResponse = new Category("Lanches", 5);
        when(categoriesRepository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        Category a = categoriesService.findById(1L);

        assertEquals(5, a.getPosition());
    }

    @Test
    void insert() {
        var request = new CategoryRequest("Lanches", null);
        when(categoriesRepository.save(any(Category.class))).thenReturn(new Category("Lanches", 1));

        var response = categoriesService.insert(request);

        assertNotNull(response);
        assertEquals(1, response.getPosition());
    }

    @Test
    void insertWithEmptyName() {
        assertThrows(
                CategoryNullException.class,
                () -> categoriesService.insert(new CategoryRequest(null, null))
        );
    }

    @Test
    void delete() {
        when(categoriesRepository.findById(1L)).thenReturn(Optional.of(new Category("Lanches", 1)));
        assertDoesNotThrow(() -> categoriesRepository.save(any(Category.class)));
        categoriesService.delete(1L);
    }
}