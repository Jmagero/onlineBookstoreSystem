package jm.onlineBookstoreSystem.entity;

import jakarta.persistence.*;
import jm.onlineBookstoreSystem.enumeration.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;

    private String author;

    @Column(unique = true, length = 5)
    private String isbn;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Integer publishedyear;

    public Book(String title, String author, String isbn, Genre genre, Integer publishedYear) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publishedyear = publishedYear;
    }
}
