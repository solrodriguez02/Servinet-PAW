package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class QuestionForm {

    @NotEmpty(message = "La pregunta no puede estar vacía")
    @Size(max=255)
    private String question;

    private long userId;

    public String getQuestion() {
        return question;
    }

    public long getUserId() {
        return userId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
