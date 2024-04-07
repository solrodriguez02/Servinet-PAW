package ar.edu.itba.paw.model;

public class ImageModel {


    private final long serviceId;
    private byte[] imageBytes;

    public ImageModel(long id, byte[] imageBytes) {
        this.serviceId=id;
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }


    public void setImageBytes(byte[] image) {
        this.imageBytes = image;
    }

}
