import model.Book;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";
    public static void main(String args[]){
        List<Book> books = new ArrayList<>();
        String startingPage = "http://books.toscrape.com/";
        webCrawler(books, startingPage);
        writeToFile(books);

        System.out.println(books.size());
    }

    public static void writeToFile(List<Book> books){
        File file = new File("BooksToScrape/resources/out.json");
        try{
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            JSONObject jsonObject = new JSONObject();
            fw.write("[\n");
            for(int i = 0; i < books.size(); i++){
                if(!(i == books.size()-1)){
                    fw.write(books.get(i).toString() + "," + "\n");
                }
                else{
                    fw.write(books.get(i) + "\n");
                }
            }
            fw.write("]");
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Check to make sure there is a next button li.next exists if exist use to fetch url and parse
     * if not exist, stop that means we are at the final page
     */
    public static void webCrawler(List<Book> books, String url){
        //parse the first page then gives the next page
        String url_page = url;

        while(url_page != null){
            url_page = webScrape(books, url_page);
//            i++;
        }

    }

    /**
     * Performs the actual web-scraping mechanics
     * @param books a list of books
     * @param url_page the url page to web scrape from
     */
    public static String webScrape(List<Book> books, String url_page){
        //.get() sends the HTTP Request then gets the HTML document
        //.userAgent = setting the HTTP request user-agent header
        //.header() = specifcy the HTTP header and the value
        try{
            Document doc = Jsoup.connect(url_page).userAgent(USER_AGENT).header("Accept-Language", "*").get();
            Element div_container = doc.getElementsByClass("row").get(1);
            Element book_container = div_container.child(1).selectFirst("ol.row");
            Elements list = book_container.getElementsByTag("li");

            for(Element e : list){
                Attributes name_link = e.selectFirst("h3").selectFirst("a").attributes();
                float price = Float.parseFloat(e.selectFirst("p.price_color").ownText().substring(1));
                String url = name_link.get("href");
                String name = name_link.get("title").replace("\"", "");
                String image = e.selectFirst("img").attributes().get("src");
                Book book = new Book(name, image, url, price);
                books.add(book);
                System.out.println(book.toString());
            }
            return getNextPage(url_page);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void iteratorPage(String url){
        try{
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).header("Accept-Language", "*").get();
            Element nextPageExist = doc.selectFirst("li.next");
            List<String> urlList = new ArrayList<>();
            if(nextPageExist != null){

                String nextPageURL =  nextPageExist.firstElementChild().attr("href");
                System.out.println(nextPageURL);
                int index = nextPageURL.indexOf("/");
                String nextPage = nextPageURL.substring(index + 1);
                System.out.println(nextPage);

                iteratorPage("http://books.toscrape.com/catalogue/" + nextPage);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getNextPage(String url){
        try{
            Document doc = Jsoup.connect(url).userAgent(USER_AGENT).header("Accept-Language", "*").get();
            Element nextPageExist = doc.selectFirst("li.next");
            List<String> urlList = new ArrayList<>();
            if(nextPageExist != null){

                String nextPageURL =  nextPageExist.firstElementChild().attr("href");
//                System.out.println(nextPageURL);
                int index = nextPageURL.indexOf("/");
                String nextPage = nextPageURL.substring(index + 1);
                System.out.println("NEXT PAGE: " + nextPage);

                return "http://books.toscrape.com/catalogue/" + nextPage;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
