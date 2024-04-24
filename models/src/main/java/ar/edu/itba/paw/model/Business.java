package ar.edu.itba.paw.model;

public class Business {
    private long businessid;
    private String businessName;
    private long userId;
    private String telephone;
    private String email;
    private String location;
    public long getBusinessid() {
        return businessid;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getName(){
        return businessName;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public long getUserId() {
        return userId;
    }


    public Business(long businessid, String businessName, long userId,String telephone, String email, String location) {
        this.businessid = businessid;
        this.userId = userId;
        this.businessName = businessName;
        this.telephone = telephone;
        this.email = email;
        this.location = location;
    }


}
