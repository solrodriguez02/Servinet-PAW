package ar.edu.itba.paw.model;

public enum EmailTypes {
    WAITING("WAITING","Creación de solicitud del turno"),
    REQUEST("REQUEST", "Solicitud del turno"),
    CANCELLED("CANCELLED","Cancelación del turno"),
    ACCEPTED("ACCEPTED", "Aceptación del turno"),
    DENIED("DENIED", "Rechazo del turno");

    private final String name;
    private final String subject;
    EmailTypes(String name, String subject){
        this.name = name;
        this.subject = subject;
    }

    public String getType(){ return name; }

    public String getSubject(){ return subject; }
}
