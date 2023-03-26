package com.example.spring_books.controllers;

import com.example.spring_books.models.Book;
import com.example.spring_books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
}
