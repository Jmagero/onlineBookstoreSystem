package jm.onlineBookstoreSystem.service;

import jm.onlineBookstoreSystem.DTO.BookDTO;
import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.enumeration.Genre;
import jm.onlineBookstoreSystem.exceptional.BookNotFoundException;
import jm.onlineBookstoreSystem.repository.BookstoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BookstoreServiceTest {
    @Mock
    private BookstoreRepository bookStoreRepository;

    @Mock
    private InventoryService inventoryService;

    private Book testBook;
    private final String isbn = "ISBNA";


    @InjectMocks
    private BookstoreService bookstoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        BookDTO bookDTO = new BookDTO("Test Title", "Test Author", "AAAAA", Genre.FICTION, 1890);
        testBook = new Book(1L,"Test Title", "Test Author","ISBNA", Genre.FICTION,  1890);
    }

    @Test
    void testAddBook() {
        BookDTO bookDTO = new BookDTO("Test Title", "Test Author", "AAAAA", Genre.FICTION, 1890);
        Book book = new Book(1L,"Test Title", "Test Author","AAAAA", Genre.FICTION,  1890);
        when(bookStoreRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookstoreService.addBook(bookDTO);

        assertNotNull(savedBook);
        assertEquals(bookDTO.getTitle(), savedBook.getTitle());
        verify(inventoryService).addBookToInventory(any(Book.class));
    }

    @Test
    void testUpdateBookFound() {
        Long bookId = 1L;
        BookDTO updatedBookDTO = new BookDTO("Updated Title", "Updated Author", "AAAAA", Genre.ROMANCE, 1890);
        Book existingBook = new Book("Test Title", "Test Author","AAAAA", Genre.FICTION,  1890);


        when(bookStoreRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookStoreRepository.save(any(Book.class))).thenReturn(existingBook);

        Book updatedBook = bookstoreService.updateBook(bookId, updatedBookDTO);

        assertNotNull(updatedBook);
        assertEquals(updatedBookDTO.getTitle(), updatedBook.getTitle());
        verify(inventoryService).updateBookInInventory(eq(bookId), any(Book.class));
    }

    @Test
    void testUpdateBookNotFound() {
        Long bookId = 1L;
        BookDTO updatedBookDTO = new BookDTO("Updated Title", "Updated Author", "2023A", Genre.NON_FICTION,  1780);

        when(bookStoreRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookstoreService.updateBook(bookId, updatedBookDTO));
    }

    @Test
    void testManageInventoryBookFound() {
        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.of(testBook));
        when(bookStoreRepository.save(any(Book.class))).thenReturn(testBook);

        Book managedBook = bookstoreService.manageInventory(isbn, true);

        assertNotNull(managedBook);
        assertEquals(isbn, managedBook.getIsbn());
        verify(inventoryService).setBookAvailability(testBook.getId(), true);
        verify(bookStoreRepository).save(testBook);
    }

    @Test
    void testManageInventoryBookNotFound() {
        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookstoreService.manageInventory(isbn, true));

        verify(inventoryService, never()).setBookAvailability(anyLong(), anyBoolean());
        verify(bookStoreRepository, never()).save(any(Book.class));
    }

    @Test
    void getAllBooks() {
    }

    @Test
    void testGetBooksByCategory() {
        Genre genre = Genre.FICTION;
        List<Book> expectedBooks = Arrays.asList(
                new Book("Book 1", "Author 1", "ISBN2", genre, 1890),
                new Book("Book 2", "Author 2", "ISBN2" ,genre, 2021 )
        );

        when(bookStoreRepository.findByGenre(genre)).thenReturn(Optional.of(expectedBooks));

        List<Book> resultBooks = bookstoreService.getBooksByCategory(genre);

        assertNotNull(resultBooks);
        assertFalse(resultBooks.isEmpty());
        assertEquals(2, resultBooks.size());
        assertEquals(genre, resultBooks.get(0).getGenre());
        verify(bookStoreRepository).findByGenre(genre);
    }

    @Test
    void testGetBooksByCategoryNotFound() {
        Genre genre = Genre.HISTORY;
        List<Book> expectedBooks = Collections.emptyList();

        when(bookStoreRepository.findByGenre(genre)).thenReturn(Optional.of(expectedBooks));

        List<Book> resultBooks = bookstoreService.getBooksByCategory(genre);

        assertNotNull(resultBooks, "Books with this category not found");
        assertTrue(resultBooks.isEmpty(), "Books with this category not found");
        verify(bookStoreRepository).findByGenre(genre);
    }

    @Test
    void testGetBookFound() {
        String isbn = "ISBN1";
        Book expectedBook = new Book("Book 1", "Author 1", "ISBN1", Genre.FICTION, 1905);

        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.of(expectedBook));

        Book resultBook = bookstoreService.getBook(isbn);

        assertNotNull(resultBook);
        assertEquals(isbn, resultBook.getIsbn());
        verify(bookStoreRepository).findByIsbn(isbn);
    }

    @Test
    void testGetBookNotFound() {
        String isbn = "ISBN-NOT-FOUND";

        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookstoreService.getBook(isbn));
    }

    @Test
    void testDeleteBookFound() {
        String isbn = "AAAAA";
        Book book = new Book("Test Title", "Test Author","AAAAA", Genre.FICTION,  1890);


        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.of(book));

        bookstoreService.deleteBook(isbn);

        verify(inventoryService).removeBookFromInventory(book.getId());
        verify(bookStoreRepository).deleteById(book.getId());
    }

    @Test
    void testDeleteBookNotFound() {
        String isbn = "1234Z";
        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookstoreService.deleteBook(isbn));
    }
}