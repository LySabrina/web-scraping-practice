
import model.Book;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Web-scraping from toscrape.com books
 * Does not use threads/parallel web scraping
 */
public class Main {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";

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
    public static List<Book> getBooks(){
        List<Book> books = new ArrayList<>();
        String startingPage = "http://books.toscrape.com/";
        webCrawler(books, startingPage);
        return books;
    }
    public static void main(String args[]){
        final long startTime = System.currentTimeMillis();
            List<Book> books = Collections.synchronizedList(new ArrayList<>());
            String startingPage = "http://books.toscrape.com/";
            webCrawler(books, startingPage);
        final long endTime = System.currentTimeMillis();
        System.out.println("TOTAL EXECUTION TIME: " + (endTime - startTime));
    }

    /**
     * Writes to a JSON file the book objects
     * @param books the list of books to write to JSON file
     */
    public static void writeToFile(List<Book> books){
        File file = new File("BooksToScrape/spring_books/resources/static/out.json");
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
     * Go through the website page by page until the end
     * @param books the list of books to add to
     * @param url the starting URL to start from
     */
    public static void webCrawler(List<Book> books, String url)  {

        String url_page = url;
        while(url_page != null ){
            url_page = webScrape(books,url_page);

        }


        //Keep looping through the website page until it's null (null means we do not have any more books)

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
                double price = Double.parseDouble(e.selectFirst("p.price_color").ownText().substring(1));

                //check if link has catalogue/ and remove if so
                String url_temp= name_link.get("href");
                System.out.println("URL TEMP : " + url_temp);
                String url;
                if((url_temp.matches("catalogue/(.*)"))){
                    url = "http://books.toscrape.com/" + url_temp;
                }
                else{
                    int index = url_temp.indexOf("/");
                    url = "http://books.toscrape.com/catalogue/" + url_temp;
                }
                String genre = getGenre(url);
                System.out.println("URL: " + url + "================>");
                String name = name_link.get("title").replace("\"", "");
                String image = e.selectFirst("img").attributes().get("src");
                Book book = new Book(name, image, url, price, genre);
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

    /**
     * Used to get the genre of the book
     * @return genre of book
     */
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
    /**
     * Tester code to see if following code can iterate over the web pages
     * @param url the url to get the HTML doc from
     */
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

    /**
     * Get the next page from the parameter url
     * @param url the url page to search for the next page
     * @return String a new url string to the next page
     */
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

                //Done this below because at page 3 and onwards, format is: /page-3.html
                //Page 1 and page 2 has the format of: /catalogue/page-2.html
                //so need to make them all uniform
                return "http://books.toscrape.com/catalogue/" + nextPage;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
