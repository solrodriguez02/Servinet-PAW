package ar.edu.itba.paw.services.exception;

public class EmailAlreadyUsedException extends RuntimeException{

    //TODO: verificar si vale la pena unificar excepciones de "0 results" de consultas a la db
    private String  email;
    private static final String message = "The email %d is already being used by another user";


    public EmailAlreadyUsedException(String email){
       this.email = email;
    }

    @Override
    public String getMessage() {
        return String.format(message, email);
    }
}
