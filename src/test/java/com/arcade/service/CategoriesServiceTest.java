package com.arcade.service;

import com.arcade.model.Category;
import com.arcade.repository.CategoriesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceTest {

    @Mock
    CategoriesRepository repository;

    @InjectMocks
    CategoriesService service;

    @Test
    void findAll() {
        List<Category> expectedResponse = List.of(
                new Category("Lanches", 1),
                new Category("Bebidas", 2),
                new Category("Porções", 3),
                new Category("Fichas", 4)
        );
        when(repository.findAll()).thenReturn(expectedResponse);

        List<Category> response = service.findAll();

        assertEquals(1, response.get(0).getPosition());
        assertEquals(2, response.get(1).getPosition());
        assertEquals(3, response.get(2).getPosition());
        assertEquals(4, response.get(3).getPosition());
    }

    @Test
    void findById() {
        Category expectedResponse = new Category("Lanches", 5);
        when(repository.findById(1L)).thenReturn(Optional.of(expectedResponse));

        Category a = service.findById(1L);

        assertEquals(5, a.getPosition());
    }

    @Test
    void insert() {
    }
}