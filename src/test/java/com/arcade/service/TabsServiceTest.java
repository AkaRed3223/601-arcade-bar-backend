package com.arcade.service;

import com.arcade.exception.TabAlreadyClosedException;
import com.arcade.model.Tab;
import com.arcade.repository.TabsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TabsServiceTest {

    @Mock
    TabsRepository tabsRepository;

    @InjectMocks
    TabsService tabsService;

    @Test
    @DisplayName("Validate Tabs - Tab must be opened when created")
    void shouldReturnOpenTabWhenCreated() {
        Tab tab = new Tab(1L, "Cesar", "111111111", 1L);
        assertTrue(tab.getIsOpen());
        assertDoesNotThrow(() -> tabsService.validateTab(tab));
    }

    @Test
    @DisplayName("Validate Tabs - Should throw exception when tab is already closed")
    void shouldThrowExceptionWhenTabIsClosed() {
        Tab tab = new Tab(1L, "Cesar", "111111111", 1L);
        tab.setIsOpen(false);
        assertFalse(tab.getIsOpen());

        TabAlreadyClosedException exception = assertThrows(TabAlreadyClosedException.class, () -> tabsService.validateTab(tab));
        assertEquals("Unable to update TAB EXTERNAL_ID-1. Tab already closed", exception.getLocalizedMessage());
    }

    @Test
    @DisplayName("Find Tabs - Should return all tabs")
    void findAll() {
        List<Tab> mockResponse = List.of(
                new Tab(1L, "Cesar", "111111111", 1L),
                new Tab(2L, "Leo", "111111111", 1L),
                new Tab(3L, "Pagan", "111111111", 1L)
        );

        when(tabsRepository.findAllByIsDeletedFalse()).thenReturn(mockResponse);

        List<Tab> response = tabsService.findAll();
        assertEquals(mockResponse, response);
        assertEquals(3, response.size());
        assertFalse(response.get(0).getIsDeleted());
        assertFalse(response.get(1).getIsDeleted());
        assertFalse(response.get(2).getIsDeleted());
    }
}