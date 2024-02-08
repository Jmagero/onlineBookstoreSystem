package jm.onlineBookstoreSystem.repository;

import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BorrowRecordsRepository extends JpaRepository<BorrowRecord, Long> {

    Optional<Book> findBorrowRecordByBook_Isbn(String isbn);

    @Query("SELECT br FROM BorrowRecord br JOIN br.book b WHERE b.isbn = :isbn")
    Optional<BorrowRecord> findByBookIsbn(String isbn);

}
