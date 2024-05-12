package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class QuestionForm {

    @NotEmpty
    @Size(max=255)
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
