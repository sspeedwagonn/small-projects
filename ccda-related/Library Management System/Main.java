package online.speedwagon;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        User user1 = new User(1, "bookluvr3","Tom Katz");
        User user2 = new User(2, "REDalREDee","Amy Stake");
        library.addUser(user1);
        library.addUser(user2);

        Book book = new Book(1, "These Violent Delights", "Micah Nemerever", 2020);
        TextBook textBook = new TextBook(2, "Java Programming", "Joyce Farrell", 2022, "Computer Science");
        Magazine magazine = new Magazine(3, "The Yale Review", "Winter 2024");

        library.addItem(book);
        library.addItem(textBook);
        library.addItem(magazine);

        library.displayAllItems();
        library.displayAllUsers();
    }
}