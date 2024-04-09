package ar.edu.itba.paw.model;

public enum EmailTypes {
    WAITING("WAITING","Turno solicitado a la espera de confirmación"),
    REQUEST("REQUEST", "Solicitud de turno"),
    CANCELLED("CANCELLED","Turno cancelado"),
    ACCEPTED("ACCEPTED", "Turno aceptado"),
    DENIED("DENIED", "Turno rechazado");

    private final String name;
    private final String subject;
    EmailTypes(String name, String subject){
        this.name = name;
        this.subject = subject;
    }

    public String getType(){ return name; }

    public String getSubject(){ return subject; }
}
