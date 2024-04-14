package ar.edu.itba.paw.services.exception;

public class AppointmentNonExistentException extends RuntimeException{

    private static final String message = "The appointment doesn't exist";

    @Override
    public String getMessage() {
        return String.format(message);
    }


}
