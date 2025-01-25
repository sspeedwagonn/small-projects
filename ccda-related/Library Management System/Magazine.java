package online.speedwagon;

public class Magazine extends LibraryItem {
    private String issue;

    public Magazine(int id, String title, String issue) {
        super(id, title);
        this.issue = issue;
    }

    @Override
    public void details() {
        System.out.println("Magazine ID: " + getId() + "\n Title: " + getTitle() + "\n Issue: " + issue);
    }
}
