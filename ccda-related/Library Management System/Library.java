package online.speedwagon;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<LibraryItem> items;
    private List<User> users;

    public Library() {
        items = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addItem(LibraryItem item) {
        items.add(item);
        System.out.println("Added item: " + item.getTitle());
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("Added user: " + user.getUsername());
    }

    public void displayAllItems() {
        System.out.println("Displaying all library items...");
        for (LibraryItem item : items) {
            item.details();
        }
    }

    public void displayAllUsers() {
        System.out.println("Displaying all library users...");
        for (User user : users) {
            user.details();
        }
    }
}
