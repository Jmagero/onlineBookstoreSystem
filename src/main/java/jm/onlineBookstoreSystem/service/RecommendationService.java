package jm.onlineBookstoreSystem.service;

import jm.onlineBookstoreSystem.entity.Book;
import jm.onlineBookstoreSystem.entity.BorrowRecord;
import jm.onlineBookstoreSystem.entity.BrowsedHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    public List<Book> generateRecommendedBooks(List<BorrowRecord> borrowRecords,
                                               List<BrowsedHistory> browsedHistory){
      List<Book> result = new ArrayList<>();
      List<Book> borrowed = borrowRecords.stream().map(BorrowRecord::getBook).collect(Collectors.toList());
      List<Book> browsed = browsedHistory.stream().map(BrowsedHistory::getBook).collect(Collectors.toList());
      List<Book> intersection = borrowedAndBrowsedBooks(borrowed, browsed);
      if(!intersection.isEmpty()){
          return intersection;
      }else{
          result.addAll(borrowed.stream().limit(3).collect(Collectors.toList()));
          result.addAll(browsed.stream().limit(3).collect(Collectors.toList()));
      }
      return result;

    }

    private List<Book> borrowedAndBrowsedBooks(List<Book> borrowed, List<Book> browsed){
        return borrowed.stream().distinct().filter(browsed::contains).collect(Collectors.toList());
    }
}
