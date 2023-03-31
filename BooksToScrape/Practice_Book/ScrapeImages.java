import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScrapeImages {
    public static final String WEB_URL = "http://books.toscrape.com/media/cache/2c/da/2cdad67c44b002e7ead0cc35693c0e8b.jpg";
    public static final String file_name = "2cdad67c44b002e7ead0cc35693c0e8b.jpg";
    public static void main(String args[]) throws IOException {
         Connection.Response resultImage = Jsoup.connect(WEB_URL).ignoreContentType(true).execute();
        File f = new File("BooksToScrape/Practice_Book/resources/" + file_name);
        FileOutputStream out = new FileOutputStream(f);
        out.write(resultImage.bodyAsBytes());
        out.close();
    }
}
