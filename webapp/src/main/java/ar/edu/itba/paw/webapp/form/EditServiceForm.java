package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.PricingTypes;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class EditServiceForm {

    @Size(max=255)
    private String description;

    private PricingTypes pricingtype;

    @Size(max=255)
    private String price;

    @Positive
    private int minimalduration;

    private boolean additionalCharges;

    public String getPrice() {
        return price;
    }

    public boolean getAdditionalCharges() {
        return additionalCharges;
    }

    public PricingTypes getPricingtype() {
        return pricingtype;
    }

    public int getMinimalduration() {
        return minimalduration;
    }
    public String getDescription() {
        return description;
    }

    public void setAdditionalCharges(boolean additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public void setMinimalduration(int minimalduration) {
        this.minimalduration = minimalduration;
    }

    public void setPricingtype(PricingTypes pricingtype) {
        this.pricingtype = pricingtype;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
