package ar.edu.itba.paw.model;

import java.time.LocalDateTime;

public class Appointment {
    private long id;
    private long serviceid;
    private long userid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean confirmed;


    public Appointment(long id, long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, Boolean confirmed) {
        this.id = id;
        this.serviceid = serviceid;
        this.userid = userid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.confirmed = confirmed;

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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setEndDate(){}

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
}
