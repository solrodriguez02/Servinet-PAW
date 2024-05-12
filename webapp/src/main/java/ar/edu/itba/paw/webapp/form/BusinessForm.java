package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BusinessForm {
    @NotEmpty
    @Size(max=255, message="El nombre del negocio no puede superar los 255 caracteres")
    private String businessName;
    @NotEmpty
    private String businessEmail;
    @NotEmpty
    //regex? pueden haber varios formatos (se podría ver con front)
    private String businessTelephone;
    @NotEmpty
    @Size(max=255, message="La ubicación del negocio no puede superar los 255 caracteres")
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
