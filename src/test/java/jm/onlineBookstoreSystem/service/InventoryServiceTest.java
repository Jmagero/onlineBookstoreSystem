package jm.onlineBookstoreSystem.service;

import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.Inventory;
import jm.onlineBookstoreSystem.exceptional.BookNotFoundException;
import jm.onlineBookstoreSystem.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Book testBook;
    private Inventory testInventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBook = new Book();
        testBook.setId(1L);
        testBook.setIsbn("ISBN1");
        testBook.setTitle("Test Book");

        testInventory = new Inventory();
        testInventory.setId(1L);
        testInventory.setBook(testBook);
        testInventory.setAvailable(true);
    }

    @Test
    void testSetBookAvailability() {
        when(inventoryRepository.findByBookId(testBook.getId())).thenReturn(Optional.of(testInventory));

        inventoryService.setBookAvailability(testBook.getId(), false);

        assertFalse(testInventory.isAvailable());
        verify(inventoryRepository).save(testInventory);
    }

    @Test
    void testCheckAvailability() {
        when(inventoryRepository.findByBookId(testBook.getId())).thenReturn(Optional.of(testInventory));

        boolean availability = inventoryService.checkAvailability(testBook.getId());

        assertTrue(availability);
    }

    @Test
    void testCheckAvailabilityNotFound() {
        when(inventoryRepository.findByBookId(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> inventoryService.checkAvailability(2L));
    }

    @Test
    void testAddBookToInventory() {
        inventoryService.addBookToInventory(testBook);

        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    void testRemoveBookFromInventory() {
        when(inventoryRepository.findByBookId(testBook.getId())).thenReturn(Optional.of(testInventory));

        inventoryService.removeBookFromInventory(testBook.getId());

        verify(inventoryRepository).delete(testInventory);
    }

    @Test
    void testRemoveBookFromInventoryNotFound() {
        when(inventoryRepository.findByBookId(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> inventoryService.removeBookFromInventory(2L));
    }

    @Test
    void testUpdateBookInInventory() {
        Book updatedBook = new Book();
        updatedBook.setId(testBook.getId());
        updatedBook.setTitle("Updated Title");

        when(inventoryRepository.findByBookId(testBook.getId())).thenReturn(Optional.of(testInventory));

        inventoryService.updateBookInInventory(testBook.getId(), updatedBook);

        assertEquals(updatedBook.getTitle(), testInventory.getBook().getTitle());
        verify(inventoryRepository).save(testInventory);
    }
}