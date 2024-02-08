package jm.onlineBookstoreSystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate borrowDate;

    @Temporal(TemporalType.DATE)
    @Transient
    private LocalDate dueDate;

    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    private boolean isPastDue = false;

    @OneToOne
    private Book book;

    private long customerId;

    public LocalDate getDueDate() {
        return borrowDate.plusDays(15);
    }
}

