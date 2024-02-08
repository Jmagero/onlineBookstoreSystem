package jm.onlineBookstoreSystem.repository;

import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT book FROM Inventory inventory JOIN inventory.book book WHERE book.isbn = :isbn")
    Book findBookByIsbn(String isbn);

    Optional<Inventory> findByBookId(Long id);
}
