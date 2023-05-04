package com.example.seleniumspring.configurations;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LaunchChrome {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "LearnSelenium/Selenium-spring/src/main/resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver webDriver = new ChromeDriver(chromeOptions);

        webDriver.get("http://books.toscrape.com/");
        WebElement next = webDriver.findElement(new By.ByClassName("next")).findElement(By.tagName("a"));
        String doc = webDriver.getPageSource();
        List<String> bookNames = getBookNames(doc);
        for (String s : bookNames){
            System.out.println(s);
        }
        next.click();
        List<String> bookNames2 = getBookNames(webDriver.getPageSource());
        for (String s : bookNames2){
            System.out.println(s);
        }
        webDriver.close();
    }

    public static List<String> getBookNames(String d){
        List<String> bookNames = new ArrayList<>();
        Document doc = Jsoup.parse(d);
        try {
            Element div_container = doc.getElementsByClass("row").get(1);
            Element book_container = div_container.child(1).selectFirst("ol.row");
            Elements list = book_container.getElementsByTag("li");
            for (Element e : list) {
                Attributes name_link = e.selectFirst("h3").selectFirst("a").attributes();
                String name = name_link.get("title").replace("\"", "");
                bookNames.add(name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bookNames;
    }
}
