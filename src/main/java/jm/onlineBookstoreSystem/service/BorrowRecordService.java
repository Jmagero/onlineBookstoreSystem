package jm.onlineBookstoreSystem.service;

import jakarta.transaction.Transactional;
import jm.onlineBookstoreSystem.exceptional.BookNotFoundException;
import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.BorrowRecord;
import jm.onlineBookstoreSystem.exceptional.CustomException;
import jm.onlineBookstoreSystem.repository.BookstoreRepository;
import jm.onlineBookstoreSystem.repository.BorrowRecordsRepository;
import jm.onlineBookstoreSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowRecordService {

    private final BookstoreRepository bookStoreRepository;
    private final BorrowRecordsRepository borrowRecordsRepository;
    private final InventoryService inventoryService;

    @Autowired
    public BorrowRecordService(CustomerRepository customerRepository, BookstoreRepository bookStoreRepository,
                               BorrowRecordsRepository borrowRecordsRepository,
                               InventoryService inventoryService) {
        this.bookStoreRepository = bookStoreRepository;
        this.borrowRecordsRepository = borrowRecordsRepository;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public BorrowRecord checkoutBook(String isbn, Long customerId) {
        Book book = bookStoreRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with given isbn: " + isbn +" doesn't exist"));

        if(inventoryService.checkAvailability(book.getId())){
            BorrowRecord borrowRecord = BorrowRecord.builder()
                    .borrowDate(LocalDate.now())
                    .book(book)
                    .customerId(customerId)
                    .dueDate(LocalDate.now().plusDays(15))
                    .build();
            BorrowRecord savedBorrowRecord = borrowRecordsRepository.save(borrowRecord);
            inventoryService.setBookAvailability(book.getId(),false);
            return savedBorrowRecord;
        } else {
            throw new BookNotFoundException("Book with given isbn: " + isbn +" is not available");
        }
    }

    @Transactional
    public void returnBook(String isbn) {
        BorrowRecord borrowRecord = borrowRecordsRepository.findByBookIsbn(isbn)
                .orElseThrow(() -> new CustomException("BorrowRecord not found"));
        LocalDate returnDate = LocalDate.now();
        borrowRecord.setReturnDate(returnDate);
        borrowRecord.setPastDue(returnDate.isAfter(borrowRecord.getDueDate()));
        borrowRecordsRepository.save(borrowRecord);
        inventoryService.setBookAvailability(borrowRecord.getBook().getId(),true);
    }
}
