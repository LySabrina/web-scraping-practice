import model.Book;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36";
    public static void main(String args[]){
        try{
            //.get() sends the HTTP Request then gets the HTML document
            //.userAgent = setting the HTTP request user-agent header
            //.header() = specifcy the HTTP header and the value
            Document doc = Jsoup.connect("http://books.toscrape.com/").userAgent(USER_AGENT).header("Accept-Language", "*").get();
            Element div_container = doc.getElementsByClass("row").get(1);
            Element book_container = div_container.child(1).selectFirst("ol.row");
            Elements list = book_container.getElementsByTag("li");

            List<Book> books = new ArrayList<>();
            for(Element e : list){
                Attributes name_link = e.selectFirst("h3").selectFirst("a").attributes();
                float price = Float.parseFloat(e.selectFirst("p.price_color").ownText().substring(1));
                String url = name_link.get("href");
                String name = name_link.get("title");
                String image = e.selectFirst("img").attributes().get("src");
                Book book = new Book(name, image, url, price);
                books.add(book);
            }
            writeToFile(books);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
}
