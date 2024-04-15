package ar.edu.itba.paw.model.exceptions;

public class AppointmentAlreadyConfirmed extends InvalidOperationException{

    private static final String message = "The appointment doesn't exist";
    private static final String APPOINTMENT_ALREADY_CONFIRMED = "turnoyaconfirmado";

    public AppointmentAlreadyConfirmed() {
        super(APPOINTMENT_ALREADY_CONFIRMED);
    }

    @Override
    public String getMessage() {
        return String.format(message);
    }


}

