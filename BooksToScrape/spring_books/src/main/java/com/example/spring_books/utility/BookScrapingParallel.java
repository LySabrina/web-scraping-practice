package com.example.spring_books.utility;

import com.example.spring_books.models.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Improvements made from the BookScraping class
 * Now have an arraylist of URL links to each page
 * Also uses multi-threaded to scrape from each page instead of one thread scraping each page
 */
public class BookScrapingParallel {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";

//    private static ArrayList<String> listURLs = new ArrayList<String>();
    private static List<String> listURLs = Collections.synchronizedList(new ArrayList<String>());
    private static List<Book> books = Collections.synchronizedList(new ArrayList<Book>());
    public static void main(String args[]){
        //All url pages ends with /page-1.html up the last one
        //the bottom of the screen shows the number of pages out of X
        //hence, make url links up to X
        long startTime = System.currentTimeMillis();

        fetchAllPageURLs();
        //Runnable that describes the task that each thread must do
        Runnable threadTask = new Runnable() {
            @Override
            public void run() {
                String thisThreadURL = getURLFromListNonThread();
//                String thisThreadURL = getURLFromList();
                parseURL(thisThreadURL);
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(10);
        int originalLength = listURLs.size();       //take the original length because in the getURLFromList will take away the URL to avoid duplication
        //Sending the length of the listURL amount of task
        for(int i =0; i < originalLength;i++){
            service.submit(threadTask);
        }

        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        long endTime = System.currentTimeMillis();
        System.out.println("FINISH TIME: " +  (endTime - startTime));
    }

    public static List<Book> getAllBooks(){
        fetchAllPageURLs();
        Runnable threadTask = new Runnable() {
            @Override
            public void run() {
                String thisThreadURL = getURLFromListNonThread();
//                String thisThreadURL = getURLFromList();
                parseURL(thisThreadURL);
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(10);
        int originalLength = listURLs.size();       //take the original length because in the getURLFromList will take away the URL to avoid duplication
        //Sending the length of the listURL amount of task
        for(int i =0; i < originalLength;i++){
            service.submit(threadTask);
        }

        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return books;
    }


    /**
     * Synchronized getURLFromList so only one thread can get a link from the arraylist
     * So they do not conflict with each other
     * Seems that synchronized vs Collections.synchronizedList will be about the same finish time
     * @return
     */
    public static synchronized String getURLFromList (){
        if(listURLs.size() > 0){
            String url = listURLs.get(0);
            listURLs.remove(url);
            return url;
        }
        return null;
    }

    public static String getURLFromListNonThread(){
        if(listURLs.size() > 0){
            String url = listURLs.get(0);
            listURLs.remove(url);
            return url;
        }
        return null;
    }
    public static void parseURL(String url){
        if(url == null){

        }
        else{
            try{
                Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
                Element div_container = doc.getElementsByClass("row").get(1);
                Element book_container = div_container.child(1).selectFirst("ol.row");
                Elements list = book_container.getElementsByTag("li");
                System.out.println("PAGE: " + url + " SCRAPED BY THREAD - " + Thread.currentThread().getName());
                for(Element e : list){
                    Attributes name_link = e.selectFirst("h3").selectFirst("a").attributes();
                    double price = Double.parseDouble(e.selectFirst("p.price_color").ownText().substring(1));

                    //check if link has catalogue/ and remove if so
                    String url_temp= name_link.get("href");
                    String book_url;
                    if((url_temp.matches("catalogue/(.*)"))){
                        book_url = "http://books.toscrape.com/" + url_temp;
                    }
                    else{
                        int index = url_temp.indexOf("/");
                        book_url = "http://books.toscrape.com/catalogue/" + url_temp;
                    }
                    String genre = getGenre(book_url);

                    String name = name_link.get("title").replace("\"", "");
                    String image = e.selectFirst("img").attributes().get("src");
                    Book book = new Book(name, image, book_url, price, genre);
                    books.add(book);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String getGenre(String url){
        try{
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).header("Accept-Language", "*").get();
            String genre = doc.selectFirst("ul.breadcrumb").child(2).child(0).ownText();
            return genre;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> fetchAllPageURLs() {
        String baseURL = "http://books.toscrape.com/catalogue/";

        try{
            Document doc = Jsoup.connect("http://books.toscrape.com/index.html").userAgent(USER_AGENT).get();
            String currentIndex = doc.selectFirst("li.current").text();
            String[] parse = currentIndex.split(" ");
            Integer lastPageNum = Integer.parseInt(parse[parse.length-1]);

            for(int i = 1 ; i <= lastPageNum; i++){
                String urlPage = baseURL + "page-" + i + ".html";
                listURLs.add(urlPage);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return listURLs;
    }

    public static List<String> getAllGenres(){
        List<String> uniqueGenres = new ArrayList<>();
        try{
            Document doc = Jsoup.connect("http://books.toscrape.com/index.html").userAgent(USER_AGENT).get();
            Element ul = doc.selectFirst("div.side_categories").child(0).child(0).child(1);
            Elements li = ul.children();
            for(Element e : li){
                uniqueGenres.add(e.text());
            }
            Collections.sort(uniqueGenres);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return uniqueGenres;
    }

}
