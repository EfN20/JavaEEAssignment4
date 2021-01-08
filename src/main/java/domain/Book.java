package domain;

public class Book {
    private String ISBN;
    private String name;
    private String author;
    private int count;
    private String url_img;

    public Book(){

    }

    public Book(String ISBN, String name, String author, int count, String url_img){
        setISBN(ISBN);
        setName(name);
        setAuthor(author);
        setCount(count);
        setUrl_img(url_img);
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
