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
    private long imageId;
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName(){
        return name + ' ' + surname;
    }

    //! TODO, temporal, deberia estar en db (cambiar en profile y navbar jsp)
    public String getProfileImg(){
        //if ( imageId==0 )
            return "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png;";
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

    public Boolean isProvider() {
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
