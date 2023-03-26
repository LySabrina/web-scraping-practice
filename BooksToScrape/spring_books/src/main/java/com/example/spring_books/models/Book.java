package model;

public class Book {
    private String name;
    private String image;
    private String url;
    private float price;
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book(String name, String image, String url, float price, String genre) {
        this.name = name;
        this.image = image;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
