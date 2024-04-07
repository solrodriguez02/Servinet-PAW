package ar.edu.itba.paw.model;

public class ImageModel {


    private final long id;
    private long serviceId;
    private byte[] imageBytes;

    public ImageModel(long id,long serviceId, byte[] imageBytes) {
        this.id=id;
        this.serviceId = serviceId;
        this.imageBytes = imageBytes;
    }

    public long getServiceId() {
        return serviceId;
    }

    public byte[] getImage() {
        return imageBytes;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setImage(byte[] image) {
        this.imageBytes = image;
    }

}
