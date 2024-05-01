package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Neighbourhoods;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

public class AppointmentForm {
    private Neighbourhoods neighbourhood;
    private String location;
    @Future
    private LocalDateTime date;
    private String time;
    private String serviceid;
    private String businessid;

    public Neighbourhoods getNeighbourhood() {
        return neighbourhood;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getServiceid() {
        return serviceid;
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setNeighbourhood(Neighbourhoods neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }
}
