package ar.edu.itba.paw.model;

public class BasicService {

        private long id;
        private long businessid;
        private String name;
        private String location;
        private long imageId;

        public BasicService(long id, long businessid, String name, String location,long imageId) {
            this.id = id;
            this.businessid = businessid;
            this.name = name;
            this.location = location;
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setBusinessid(long businessid) {
            this.businessid = businessid;
        }

        public long getBusinessid() {
            return businessid;
        }

        public long getImageId() {
        return imageId;
    }

        public void setImageId(long imageId) {
            this.imageId = imageId;
        }
}
