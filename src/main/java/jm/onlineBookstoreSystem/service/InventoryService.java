package jm.onlineBookstoreSystem.service;

import jm.onlineBookstoreSystem.exceptional.BookNotFoundException;
import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.Inventory;
import jm.onlineBookstoreSystem.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public void setBookAvailability(Long bookId, boolean availability) {
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException(
                        "Inventory  doesn't exist"
                ));
        inventory.setAvailable(availability);
        inventoryRepository.save(inventory);
    }
    public boolean checkAvailability(Long bookId) {
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not is not available" ));
        return inventory.isAvailable();
    }
    public void addBookToInventory(Book book) {
        Inventory inventory = new Inventory();
        inventory.setBook(book);
        inventory.setAvailable(true);
        inventoryRepository.save(inventory);
    }

    public void removeBookFromInventory(Long bookId) {
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                        .orElseThrow(() -> new BookNotFoundException("Book with given id: " + bookId +" doesn't exist"));
        inventoryRepository.delete(inventory);
    }
    public void updateBookInInventory(Long bookId, Book book) {
        Inventory inventory = inventoryRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with given id: " + bookId +" doesn't exist"));
        inventory.setBook(book);
        inventoryRepository.save(inventory);
    }
}
