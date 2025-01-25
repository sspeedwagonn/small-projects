package online.speedwagon;

public class User {
    private int userID;
    private String username;
    private String fullName;

    public User(int userID, String username, String fullName) {
        this.userID = userID;
        this.username = username;
        this.fullName = fullName;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void details() {
        System.out.println("UserID: " + userID + "\n Username: " + username + "\n Full Name: " + fullName);
    }
}
