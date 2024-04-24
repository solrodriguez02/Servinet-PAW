package ar.edu.itba.paw.model.exceptions;

public class ForbiddenOperation extends InvalidOperationException {

    private static final String message = "Forbidden operation";
    private static final String FORBIDDEN = "operacionprohibida";

    public ForbiddenOperation() {
        super(FORBIDDEN);
    }


    @Override
    public String getMessage() {
        return message;
    }
}

