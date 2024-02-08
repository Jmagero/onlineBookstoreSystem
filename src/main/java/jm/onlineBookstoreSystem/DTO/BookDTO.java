package jm.onlineBookstoreSystem.DTO;

import jm.onlineBookstoreSystem.enumeration.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

    private String title;

    private String author;

    private String isbn;

    private Genre genre;

    private Integer publishedyear;
}
