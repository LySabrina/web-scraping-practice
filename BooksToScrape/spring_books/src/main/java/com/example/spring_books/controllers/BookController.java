package com.example.spring_books.controllers;

import com.example.spring_books.models.Book;
import com.example.spring_books.repository.BookRepository;
import com.example.spring_books.utility.BookScraping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("http://localhost:3000")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    public BookController() {

    }
    @PostMapping("/book")
    public Book newBook(@RequestBody Book book){
        return bookRepository.save(book);
    }
    @GetMapping("/book")
    public List<Book> getAllBooks(){
        return (List<Book>) bookRepository.findAll();
    }
    @GetMapping("/genres")
    public List<String> getUniqueGenres(){
        return BookScraping.getAllGenres();
    }
}
