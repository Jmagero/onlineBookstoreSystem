package jm.onlineBookstoreSystem.service;

import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.BorrowRecord;
import jm.onlineBookstoreSystem.enumeration.Genre;
import jm.onlineBookstoreSystem.exceptional.BookNotFoundException;
import jm.onlineBookstoreSystem.exceptional.CustomException;
import jm.onlineBookstoreSystem.repository.BookstoreRepository;
import jm.onlineBookstoreSystem.repository.BorrowRecordsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowRecordServiceTest {

    @Mock
    private BookstoreRepository bookStoreRepository;

    @Mock
    private BorrowRecordsRepository borrowRecordsRepository;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private BorrowRecordService borrowRecordService;

    private final String isbn = "123456789";
    private Book testBook;
    private BorrowRecord testBorrowRecord;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testBook = new Book(1L,"Test Title", "Test Author","ISBNA", Genre.FICTION,  1890);

        testBorrowRecord = BorrowRecord.builder()
                .borrowDate(LocalDate.now())
                .book(testBook)
                .build();
        testBorrowRecord.setDueDate(LocalDate.now().plusDays(14));

    }

    @Test
    void testCheckoutBookSuccess() {
        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.of(testBook));
        when(inventoryService.checkAvailability(testBook.getId())).thenReturn(true);
        when(borrowRecordsRepository.save(any(BorrowRecord.class))).thenReturn(testBorrowRecord);

        BorrowRecord result = borrowRecordService.checkoutBook(isbn, 1L);

        assertNotNull(result);
        verify(bookStoreRepository).findByIsbn(isbn);
        verify(inventoryService).checkAvailability(testBook.getId());
        verify(inventoryService).setBookAvailability(testBook.getId(), false);
        verify(borrowRecordsRepository).save(any(BorrowRecord.class));
    }

    @Test
    void testCheckoutBookNotFound() {
        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> borrowRecordService.checkoutBook(isbn, 1L));

        verify(inventoryService, never()).checkAvailability(anyLong());
        verify(borrowRecordsRepository, never()).save(any(BorrowRecord.class));
    }

    @Test
    void testCheckoutBookNotAvailable() {
        when(bookStoreRepository.findByIsbn(isbn)).thenReturn(Optional.of(testBook));
        when(inventoryService.checkAvailability(testBook.getId())).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> borrowRecordService.checkoutBook(isbn, 1L));

        verify(bookStoreRepository).findByIsbn(isbn);
        verify(inventoryService).checkAvailability(testBook.getId());
        verify(borrowRecordsRepository, never()).save(any(BorrowRecord.class));
    }

    @Test
    void testReturnBook() {
        when(borrowRecordsRepository.findByBookIsbn(isbn)).thenReturn(Optional.of(testBorrowRecord));

        borrowRecordService.returnBook(isbn);

        assertEquals(LocalDate.now(), testBorrowRecord.getReturnDate());

        boolean pastDue = LocalDate.now().isAfter(testBorrowRecord.getDueDate());
        assertEquals(pastDue, testBorrowRecord.isPastDue());

        verify(borrowRecordsRepository).save(testBorrowRecord);

        verify(inventoryService).setBookAvailability(testBorrowRecord.getBook().getId(), true);
    }

    @Test
    void testReturnBook_BorrowRecordNotFound() {
        when(borrowRecordsRepository.findByBookIsbn(isbn)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> borrowRecordService.returnBook(isbn));
    }
}