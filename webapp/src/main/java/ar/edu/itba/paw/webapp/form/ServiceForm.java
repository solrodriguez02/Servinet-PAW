package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Categories;
import ar.edu.itba.paw.model.Neighbourhoods;
import ar.edu.itba.paw.model.PricingTypes;
import ar.edu.itba.paw.webapp.validation.LocationFormatted;
import ar.edu.itba.paw.webapp.validation.ValidImageFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@LocationFormatted
public class ServiceForm {

   @Size(max=255)
   @NotEmpty
   @NotNull
   private String title;

   @Size(max=255)
   private String description;

   @NotNull
   private boolean homeserv;

   @Size(max=255)
   private String location;

   private Categories category;

   @ValidImageFile
   private MultipartFile image;

   private Neighbourhoods[] neighbourhoods;

   private PricingTypes pricingtype;

   @Size(max=255)
   private String price;

   @Positive
   private int minimalduration;

   private boolean additionalCharges;

    public String getTitle() {
         return title;
    }

   public String getPrice() {
      return price;
   }

   public String getLocation() {
      return location;
   }

   public boolean getAdditionalCharges() {
      return additionalCharges;
   }

   public boolean getHomeserv() {
      return homeserv;
   }

   public Categories getCategory() {
      return category;
   }

   public PricingTypes getPricingtype() {
      return pricingtype;
   }
   public Neighbourhoods[] getNeighbourhood() {
      return neighbourhoods;
   }

   public int getMinimalduration() {
      return minimalduration;
   }
   public String getDescription() {
      return description;
   }
   public MultipartFile getImage() {
      return image;
   }

   public void setAdditionalCharges(boolean additionalCharges) {
      this.additionalCharges = additionalCharges;
   }

   public void setHomeserv(boolean homeserv) {
      this.homeserv = homeserv;
   }

   public void setImage(MultipartFile image) {
      this.image = image;
   }

   public void setMinimalduration(int minimalduration) {
      this.minimalduration = minimalduration;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setPricingtype(PricingTypes pricingtype) {
      this.pricingtype = pricingtype;
   }

   public void setNeighbourhood(Neighbourhoods[] neighbourhood){
       this.neighbourhoods = neighbourhood;
   }
   public void setLocation(String location) {
      this.location = location;
   }

   public void setPrice(String price) {
      this.price = price;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setCategory(Categories category) {
      this.category = category;
   }
}
