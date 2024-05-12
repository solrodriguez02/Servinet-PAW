package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BusinessForm {
    @NotEmpty
    @Size(max=255)
    private String businessName;

    @NotEmpty
    @Email
    @Size(max=255)
    private String businessEmail;

    @NotEmpty
    @Pattern(regexp = "^\\+(\\d{1,3})?\\s?9?\\s?(\\d{1,4})?\\s?(\\d{6,8})$")
    @Size(max=255)
    private String businessTelephone;

    @NotEmpty
    @Size(max=255)
    //regex? calle, altura, barrio(CABA), depto (?), piso (?) -> (?) opcionales
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
