package online.speedwagon;

public class Book extends LibraryItem {
    private String author;
    private int yearPublished;

    public Book(int id, String title, String author, int yearPublished) {
        super(id, title);
        this.author = author;
        this.yearPublished = yearPublished;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    @Override
    public void details() {
        System.out.println("Book ID: " + getId() + "\n Title: " + getTitle() + "\n Author: " + author + "\n Year Published: " + yearPublished);
    }
}
