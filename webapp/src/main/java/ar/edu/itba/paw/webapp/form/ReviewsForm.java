package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ReviewsForm {

    @Min(value=1)
    @Max(value=5)
    private int rating;

    @Size(min=1, max=255)
    private String comment;

    private long questionUserId;

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public long getQuestionUserId() {
        return questionUserId;
    }

    public void setQuestionUserId(long userId) {
        this.questionUserId = userId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
