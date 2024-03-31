package ar.edu.itba.paw.webapp.exception;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 676070613730583976L;

    //TODO: verificar si vale la pena unificar excepciones de "0 results" de consultas a la db
    private static String  username;
    private static final String message = "The user with id: %s was not found";


    public UserNotFoundException(String user){
       this.username = user;
    }

    @Override
    public String getMessage() {
        return String.format(message, username);
    }
}
