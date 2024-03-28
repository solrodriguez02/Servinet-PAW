package ar.edu.itba.paw.model;

public class User {
    private String username;
    private long userId;

    public User(long userId, String username) {
        this.username = username;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}