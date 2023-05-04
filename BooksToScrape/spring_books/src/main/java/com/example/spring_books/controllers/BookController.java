package com.example.spring_books.controllers;

import com.example.spring_books.models.Book;
import com.example.spring_books.repository.BookRepository;
import com.example.spring_books.service.BookScrapingParallel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
        return BookScrapingParallel.getAllGenres();
    }

    @GetMapping("/book/{id}")
    public Book getBookDetails(@PathVariable Long id){
        Optional<Book> bookResponse = bookRepository.findById(id);
         Book myBook = bookResponse.get();
        return myBook;
    }

    @GetMapping("/images")
    public List<String> getAllBookImages() throws IOException {
        File imageFolder = new File("BooksToScrape/spring_books/src/main/resources/static/images");
        File[] files = imageFolder.listFiles();
        List<String> myImages = new ArrayList<>();
        for(File f : files){
            //convert to binary data?
            // something about it being safer and does not corrupt the binary data as we send it over the API?
            byte[] fileContents = FileUtils.readFileToByteArray(f);
            String encodedFile = Base64.getEncoder().encodeToString(fileContents);
            myImages.add(encodedFile);
        }
        return myImages;
    }
    @GetMapping("/catalogue/{genre}")
    public List<Book> getGenreBooks(@PathVariable String genre){
        return bookRepository.getAllBooksByGenre(genre);
    }

}
