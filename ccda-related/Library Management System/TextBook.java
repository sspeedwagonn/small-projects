package online.speedwagon;

public class TextBook extends Book {
    private String subject;
    public TextBook(int id, String title, String author, int yearPublished, String subject) {
        super(id, title, author, yearPublished);
        this.subject = subject;
    }

    @Override
    public void details() {
        System.out.println("Textbook ID: " + getId() + "\n Title: " + getTitle() + "\n Author: " + getAuthor() + "\n Year Published: " + getYearPublished());
    }
}
