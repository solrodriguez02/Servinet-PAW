package ar.edu.itba.paw.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class BasicAppointment {
    private final long id;
    private final long serviceid;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String location;
    private boolean confirmed;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE dd MMMM");
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("EEE dd MMMM yyyy, HH:mm");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private final String startDateString;
    private final String HOMESERVICE="-";

    public BasicAppointment(long id, long serviceid, LocalDateTime startDate, LocalDateTime endDate, String location, boolean confirmed) {
        this.id = id;
        this.serviceid = serviceid;
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
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public String getStartDateString() {
        return startDate.format(dateFormat);
    }
    public String getStartDateWithTimeString(){
        return startDate.format(dateTimeFormat);
    }
    public String getStartDateTimeString(){
        return startDate.format(timeFormat);
    }
    public String getEndDateTimeString(){
        return endDate.format(timeFormat);
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public boolean getConfirmed() {
        return confirmed;
    }
    public String getLocation() {
        return location;
    }

    public void setEndDate(){}

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean getHomeService(){
        return !Objects.equals(location, HOMESERVICE);
    }

    public boolean getDuration(){
        return !startDate.equals(endDate);
    }
}
