package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class EditReviewForm {

    @Min(value=1, message = "Debe seleccionar una calificacion")
    @Max(value=5, message = "Debe seleccionar una calificacion")
    private int editedRating;

    @Size(max=255)
    private String editedComment;

    public int getEditedRating() {
        return editedRating;
    }

    public String getEditedComment() {
        return editedComment;
    }

    public void setEditedRating(int rating) {
        this.editedRating = rating;
    }

    public void setEditedComment(String comment) {
        this.editedComment = comment;
    }

}
