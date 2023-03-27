package com.example.spring_books.models;

import com.example.spring_books.repository.BookRepository;
import com.example.spring_books.utility.BookScraping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        List<Book> listBook = new ArrayList<>();
        listBook = BookScraping.getBooks();
        if(!(listBook == null)){
            for(Book b : listBook){
                bookRepository.save(b);
                System.out.println("Added " + b.getName());
            }
            System.out.println(":: Database has been initialized");
        }
        else{
            System.out.println(":: DB INITIALIZATION - FAILED");
        }
    }
}
