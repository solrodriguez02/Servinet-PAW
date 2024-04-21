package ar.edu.itba.paw.model;

import java.time.LocalDate;

public class Question {
    private long id;
    private long serviceid;
    private long userid;
    private String question;
    private String response;
    private LocalDate date;

    public Question(long id, long serviceid, long userid, String question, String response, LocalDate date) {
        this.id = id;
        this.serviceid = serviceid;
        this.userid = userid;
        this.question = question;
        this.response = response;
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

    public String getQuestion() {
        return question;
    }

    public String getResponse() {
        return response;
    }

    public LocalDate getDate() {
        return date;
    }
}
