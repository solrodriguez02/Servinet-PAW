package ar.edu.itba.paw.model;

public class ImageModel {


    private final long imageId;
    private byte[] imageBytes;

    public ImageModel(long imageId, byte[] imageBytes) {
        this.imageId=imageId;
        this.imageBytes = imageBytes;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
    public long getImageId() {
        return this.imageId;
    }

    public void setImageBytes(byte[] image) {
        this.imageBytes = image;
    }

}
