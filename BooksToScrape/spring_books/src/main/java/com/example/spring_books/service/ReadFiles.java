package com.example.spring_books.service;

import java.io.File;

public class ReadFiles {
    public static void main (String args[]){
        File imageFolder = new File("BooksToScrape/spring_books/src/main/resources/static/images");
        if(imageFolder.canRead()){
            System.out.println("YEA");
        }
        else{
            System.out.println("mah");
        }
    }
}
