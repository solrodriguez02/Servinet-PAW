package ar.edu.itba.paw.model;

import java.time.LocalDate;

public class Rating {
    private long id;
    private long serviceid;
    private long userid;
    private int rating;
    private String comment;
    private LocalDate date;

    public Rating(long id, long serviceid, long userid, int rating, String comment, LocalDate date) {
        this.id = id;
        this.serviceid = serviceid;
        this.userid = userid;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
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

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }
}
