package jm.onlineBookstoreSystem.controller;

import jm.onlineBookstoreSystem.DTO.BookDTO;
import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.BorrowRecord;
import jm.onlineBookstoreSystem.enumeration.Genre;
import jm.onlineBookstoreSystem.exceptional.AuthorizationException;
import jm.onlineBookstoreSystem.service.BookstoreService;
import jm.onlineBookstoreSystem.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static jm.onlineBookstoreSystem.constants.Constants.ADMIN_STRING;


@RestController
@RequestMapping("/api/v1/books")

public class BookstoreController {

    private final BookstoreService bookstoreService;
    private final BorrowRecordService borrowRecordService;


    @Autowired
    public BookstoreController(BookstoreService bookstoreService,
                               BorrowRecordService borrowRecordService) {
        this.bookstoreService = bookstoreService;
        this.borrowRecordService = borrowRecordService;
    }


    // API to add new books
    @PostMapping()
    public ResponseEntity<Book> addBook(@RequestHeader("Authorization") String authHeader,@RequestBody BookDTO bookDTO) {
        if(ADMIN_STRING.equalsIgnoreCase(authHeader)){
            return new ResponseEntity<>(bookstoreService.addBook(bookDTO),HttpStatus.CREATED);
        }
        throw new AuthorizationException("You are not authorized to add a book");
    }

    // API to update book details
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@RequestHeader("Authorization") String authHeader, @PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        if(ADMIN_STRING.equalsIgnoreCase(authHeader)){
            return new ResponseEntity<>(bookstoreService.updateBook(id, updatedBookDTO), HttpStatus.OK);
        }
        throw new AuthorizationException("You are not authorized to add a book");

    }

    // API to manage inventory
    @PutMapping("/manage/{isbn}")
    public ResponseEntity<Book> manageInventory(@RequestHeader("Authorization") String authHeader, @PathVariable String isbn, @RequestParam boolean isAvailable) {
        if(ADMIN_STRING.equalsIgnoreCase(authHeader)){
            return new ResponseEntity<>(bookstoreService.manageInventory(isbn, isAvailable), HttpStatus.OK);
        }
        throw new AuthorizationException("You are not authorized to add a book");

    }

    // API to browse books by category
    @GetMapping("/browse/{category}")
    public ResponseEntity<List<Book>> browseBooksByCategory(@PathVariable String category) {
            return ResponseEntity.ok(bookstoreService.getBooksByCategory(Genre.valueOf(category.toUpperCase())));
    }

    // API to view book details
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> viewBookDetails(@PathVariable String isbn) {
        return new ResponseEntity<>(bookstoreService.getBook(isbn),HttpStatus.OK);
    }
    @PostMapping("/borrow/{isbn}/{customerId}")
    public ResponseEntity<BorrowRecord> requestToBorrowBook(@PathVariable String isbn, @PathVariable Long customerId) {
        return ResponseEntity.ok(borrowRecordService.checkoutBook(isbn, customerId));
    }

    @PostMapping("/return/{isbn}")
    public ResponseEntity<?> requestToReturnBook(@PathVariable String isbn) {
            borrowRecordService.returnBook(isbn);
            return  ResponseEntity.ok().build();
    }

    //delete book
    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@RequestHeader("Authorization") String authHeader, @PathVariable String isbn){
        if(ADMIN_STRING.equalsIgnoreCase(authHeader)){
            bookstoreService.deleteBook(isbn);
            return  ResponseEntity.noContent().build();
        }
        throw new AuthorizationException("You are not authorized to add a book");
    }

    @GetMapping()
    public ResponseEntity<List<Book>> findAllBooks(){
        return new ResponseEntity<>(bookstoreService.getAllBooks(), HttpStatus.OK);
    }

}


