package ar.edu.itba.paw.model;

public class Service extends BasicService {

    private String description;
    private boolean homeService;
    private final String[] neighbourhoodAvailable;
    private int duration;
    private PricingTypes pricing;
    private String price;
    private Categories category;
    private boolean additionalCharges;

    public Service(long id, long businessid, String name, String description, boolean homeService, String location,String[] neighbourhoodAvailable, Categories category, int duration, PricingTypes pricingType, String price, boolean additionalCharges,long imageId) {
        super(id, businessid, name, location, imageId);
        this.description = description;
        this.homeService = homeService;
        this.neighbourhoodAvailable = neighbourhoodAvailable;
        this.category = category;
        this.duration = duration;
        this.pricing = pricingType;
        this.price = price;
        this.additionalCharges = additionalCharges;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category.getValue();
    }

    public void setCategory(String category) {
        this.category = Categories.findByValue(category);
    }

    public boolean getHomeService() {
        return homeService;
    }

    public void setHomeService(boolean homeService) {
        this.homeService = homeService;
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

    public boolean getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(boolean additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

}
