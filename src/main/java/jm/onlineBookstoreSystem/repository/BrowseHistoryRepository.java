package jm.onlineBookstoreSystem.repository;


import jm.onlineBookstoreSystem.entity.BrowsedHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrowseHistoryRepository  extends JpaRepository<BrowsedHistory, Long> {
}
