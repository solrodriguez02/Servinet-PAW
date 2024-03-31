package ar.edu.itba.paw.model;

public class User {
    private String username;
    private long userId;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String telephone;

    private Boolean isProvider;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Boolean getProvider() {
        return isProvider;
    }

    public User(long userId, String username,String password ,String name, String surname, String email, String telephone, Boolean isProvider) {
        this.username = username;
        this.userId = userId;
        this.password=password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.isProvider = isProvider;
    }
    public String getPassword() {
        return password;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}