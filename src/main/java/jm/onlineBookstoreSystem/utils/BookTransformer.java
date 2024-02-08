package jm.onlineBookstoreSystem.utils;

import jm.onlineBookstoreSystem.DTO.BookDTO;
import jm.onlineBookstoreSystem.entity.Book;

public class BookTransformer {
    public  static Book fromBookDtoToBook(BookDTO bookDTO){
        return Book.builder()
                .author(bookDTO.getAuthor())
                .genre(bookDTO.getGenre())
                .title(bookDTO.getTitle())
                .publishedyear(bookDTO.getPublishedyear())
                .isbn(bookDTO.getIsbn())
                .build();
    }
}
