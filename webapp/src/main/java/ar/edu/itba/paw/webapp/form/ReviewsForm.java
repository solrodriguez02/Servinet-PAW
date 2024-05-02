package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ReviewsForm {

    @Min(value=1, message = "Debe seleccionar una calificacion")
    @Max(value=5, message = "Debe seleccionar una calificacion")
    private int rating;

    @Size(max=255)
    private String comment;

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
