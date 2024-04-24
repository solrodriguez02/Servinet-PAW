package ar.edu.itba.paw.webapp.form;

public class BusinessForm {
    private String businessName;
    private String businessEmail;
    private String businessTelephone;
    private String businessLocation;

    public String getBusinessName() {
        return businessName;
    }
    public String getBusinessEmail() {
        return businessEmail;
    }

    public String getBusinessTelephone() {
        return businessTelephone;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public void setBusinessTelephone(String businessTelephone) {
        this.businessTelephone = businessTelephone;
    }
}
