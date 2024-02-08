package jm.onlineBookstoreSystem.service;

import jm.onlineBookstoreSystem.DTO.BookDTO;
import jm.onlineBookstoreSystem.enumeration.Genre;
import jm.onlineBookstoreSystem.exceptional.BookNotFoundException;
import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.exceptional.CustomException;
import jm.onlineBookstoreSystem.repository.BookstoreRepository;
import jm.onlineBookstoreSystem.utils.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service

public class BookstoreService {
    private final BookstoreRepository bookStoreRepository;
    private final InventoryService inventoryService;

    @Autowired
    public BookstoreService(BookstoreRepository bookStoreRepository,
                            InventoryService inventoryService) {
        this.bookStoreRepository = bookStoreRepository;
        this.inventoryService = inventoryService;
    }

    public Book addBook(BookDTO bookDTO) {
        Book book = BookTransformer.fromBookDtoToBook(bookDTO);
        Book savedBook = bookStoreRepository.save(book);
        inventoryService.addBookToInventory(savedBook);
        return savedBook;
    }

    // Update book details
    public Book updateBook(Long id, BookDTO updatedBook) {
        Optional<Book> optionalBook = bookStoreRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setGenre(updatedBook.getGenre());
            book.setPublishedyear(updatedBook.getPublishedyear());
            book.setIsbn(updatedBook.getIsbn());
            Book savedBook = bookStoreRepository.save(book);
            inventoryService.updateBookInInventory(id,savedBook);
            return savedBook;
        } else {
            throw new BookNotFoundException("Book with given id: " + id +" doesn't exist");
        }
    }

    // Manage inventory
    public Book manageInventory(String isbn,boolean isAvailable) {
        Optional<Book> optionalBook = bookStoreRepository.findByIsbn(isbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            inventoryService.setBookAvailability(book.getId(), isAvailable);
            return bookStoreRepository.save(book);
        } else {
            throw new BookNotFoundException("Book with given isbn: " + isbn +" doesn't exist");
        }
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookStoreRepository.findAll();
    }

    // Get books by category
    public List<Book> getBooksByCategory(Genre category) {
        return bookStoreRepository.findByGenre(category).orElseThrow(
                () -> new CustomException("Books with this category: " + category+ "not found")
        );
    }

    // Get book by ID
    public Book getBook(String isbn) {
        return bookStoreRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book with given isbn: " + isbn +" doesn't exist"));
    }

    // Delete a book by ID
    public void deleteBook(String isbn) {
        Optional<Book> optionalBook = bookStoreRepository.findByIsbn(isbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            inventoryService.removeBookFromInventory(book.getId());
            bookStoreRepository.deleteById(book.getId());
        } else {
            throw new BookNotFoundException("Book with given isbn: " + isbn +" doesn't exist");
        }
    }

}

