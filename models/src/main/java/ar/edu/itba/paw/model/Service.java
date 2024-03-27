package ar.edu.itba.paw.model;

public class Service {
    private long id;
    private String name;
    private String description;
    private String location;
    private String category;

    // Constructor
    public Service(long id, String name, String description, String location, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
