package jm.onlineBookstoreSystem.repository;

import jm.onlineBookstoreSystem.enumeration.Genre;
import jm.onlineBookstoreSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookstoreRepository extends JpaRepository<Book, Long> {


    Optional<List<Book>> findByGenre(Genre genre);

    Optional<Book> findByIsbn(String isbn);
}
