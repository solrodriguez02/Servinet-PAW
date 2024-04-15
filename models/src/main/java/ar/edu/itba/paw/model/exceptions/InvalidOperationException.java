package ar.edu.itba.paw.model.exceptions;

public class InvalidOperationException extends RuntimeException {
    private final String argument;

    public InvalidOperationException(String argument){
        this.argument = argument;
    }

    public String getArgument(){ return this.argument; }
}
