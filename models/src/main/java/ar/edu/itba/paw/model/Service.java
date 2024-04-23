package ar.edu.itba.paw.model;

import java.util.Arrays;

public class Service {
    private long id;
    private long businessid;
    private String name;
    private String description;
    private Boolean homeService;
    private String location;
    private final String[] neighbourhoodAvailable;
    private int duration;
    private PricingTypes pricing;
    private String price;
    private Categories category;
    private Boolean additionalCharges;
    private final long imageId;

    // Constructor
    public Service(long id, long businessid, String name, String description, Boolean homeService, String location,String[] neighbourhoodAvailable, Categories category, int duration, PricingTypes pricingType, String price, Boolean additionalCharges,long imageId) {
        this.id = id;
        this.businessid = businessid;
        this.name = name;
        this.description = description;
        this.homeService = homeService;
        this.location = location;
        this.neighbourhoodAvailable = neighbourhoodAvailable;
        this.category = category;
        this.duration = duration;
        this.pricing = pricingType;
        this.price = price;
        this.additionalCharges = additionalCharges;
        this.imageId= imageId;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public long getImageId() {
        return imageId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category.getValue();
    }

    public void setCategory(String category) {
        this.category = Categories.findByValue(category);
    }

    public Boolean getHomeService() {
        return homeService;
    }

    public void setHomeService(Boolean homeService) {
        this.homeService = homeService;
    }

    public void setBusinessid(long businessid) {
        this.businessid = businessid;
    }

    public long getBusinessid() {
        return businessid;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String[] getNeighbourhoodAvailable() {
        return neighbourhoodAvailable;
    }



    public String getPricing() {
        return pricing.getValue();
    }

    public void setPricing(String pricing) {
        this.pricing = PricingTypes.findByValue(pricing);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(Boolean additionalCharges) {
        this.additionalCharges = additionalCharges;
    }
}
