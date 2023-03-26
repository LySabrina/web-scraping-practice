package com.example.spring_books.repository;

import com.example.spring_books.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
