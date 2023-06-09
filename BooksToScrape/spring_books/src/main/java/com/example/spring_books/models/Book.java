package com.example.spring_books.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue (strategy =  GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String image;
    private String imageRef;
    private String url;
    private double price;
    private String genre;

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book() {
    }

    public Book(String name, String image, String imageRef, String url, double price, String genre) {
        this.name = name;
        this.image = image;
        this.imageRef = imageRef;
        this.url = url;
        this.price = price;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" + "\"name\" : " + "\"" + name +"\"," +
                "\"image\" : " + "\"" + image + "\"," +
                "\"url\" : " + "\"" + url +"\"," +
                "\"price\" : " +  price  +","+
                "\"genre\" : " + "\"" +  genre + "\"" +
                "}";
    }
}
