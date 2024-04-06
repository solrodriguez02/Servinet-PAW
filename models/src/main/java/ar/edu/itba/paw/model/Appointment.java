package ar.edu.itba.paw.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private long id;
    private long serviceid;
    private long userid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private Boolean confirmed;
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE dd MMMM yyyy, HH:mm");
    private String startDateString;


    public Appointment(long id, long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, String location, Boolean confirmed) {
        this.id = id;
        this.serviceid = serviceid;
        this.userid = userid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.confirmed = confirmed;
        this.location = location;
        this.startDateString = startDate.format(dateFormat);
    }

    public long getId() {
        return id;
    }

    public long getServiceid() {
        return serviceid;
    }

    public long getUserid() {
        return userid;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public String getStartDateString() {
        return startDate.format(dateFormat);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }
    public String getLocation() {
        return location;
    }

    public void setEndDate(){}

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
