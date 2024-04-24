package ar.edu.itba.paw.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment extends BasicAppointment{

    private final long userid;

    public Appointment(long id, long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, String location, Boolean confirmed) {
        super(id, serviceid, startDate, endDate, location, confirmed);
        this.userid = userid;
    }

    public long getUserid() {
        return userid;
    }

}
