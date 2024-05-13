package ar.edu.itba.paw.model;

public class User {
    private String username;
    private long userId;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private boolean isProvider;
    private String locale;
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName(){
        return name + ' ' + surname;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public boolean getProvider() {
        return isProvider;
    }

    public boolean isProvider() {
        return isProvider;
    }

    public User(long userId, String username,String password ,String name, String surname, String email, String telephone, boolean isProvider, String locale) {
        this.username = username;
        this.userId = userId;
        this.password=password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.isProvider = isProvider;
        this.locale = locale;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
