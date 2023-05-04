package com.example.seleniumspring.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScraperService {
    public static final String URL = "https://relatedwords.org/relatedto/";
    private final ChromeDriver driver = new ChromeDriver();

    @PostConstruct
    public void postConstruct(){
        scrape("summer");
    }
    public void scrape(final String value){
        //loads the page with ChromeDriver
        driver.get(URL + value);
        final WebElement words = driver.findElement(new By.ByClassName("words"));
        final List<WebElement> wordList = words.findElements(By.tagName("a"));
        wordList.forEach(word -> System.out.println(word.getText()));
        driver.quit();

    }
}
