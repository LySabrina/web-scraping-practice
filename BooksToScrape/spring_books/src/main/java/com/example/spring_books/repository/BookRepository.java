package com.example.spring_books.repository;

import com.example.spring_books.models.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("SELECT * FROM Book b WHERE b.genre = ?1")
    List<Book> getAllBooksByGenre(String genre);


}
