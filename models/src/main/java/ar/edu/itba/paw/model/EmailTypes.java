package ar.edu.itba.paw.model;

import static ar.edu.itba.paw.model.EmailTemplates.*;

public enum EmailTypes {
    WAITING("WAITING","Creación de solicitud del turno"),
    REQUEST("REQUEST", "Solicitud del turno"),
    CANCELLED("CANCELLED","Cancelación del turno"),
    ACCEPTED("ACCEPTED", "Aceptación del turno"),
    DENIED("DENIED", "Rechazo del turno"),
    CREATED_SERVICE("CREATED", "Creación del servicio", SERVICE_TEMPLATE),
    DELETED_SERVICE("DELETED", "Eliminación del servicio", SERVICE_TEMPLATE),
    CREATED_BUSINESS("CREATED", "Creación del negocio", BUSINESS_TEMPLATE),
    DELETED_BUSINESS("DELETED", "Eliminación del negocio", BUSINESS_TEMPLATE),
    PASSWORD_RECOVER("RECOVER_PASSWORD", "Recuperación de contraseña", RECOVER_PASSWORD_TEMPLATE),
    CONFIRM_NEW_PASSWORD("CONFIRM_NEW_PASSWORD", "Nueva contraseña definida con éxito", CONFIRM_NEW_PASSWORD_TEMPLATE);

    private final String name;
    private final String subject;
    private final EmailTemplates template;
    private final Boolean isAboutAppointment;

    EmailTypes(String name, String subject) {
        this.name = name;
        this.subject = subject;
        this.template = APPOINTMENT_TEMPLATE;
        this.isAboutAppointment = true;
    }

    EmailTypes(String name, String subject, EmailTemplates template) {
        this.name = name;
        this.subject = subject;
        this.template = template;
        this.isAboutAppointment = false;
    }

    public String getType(){ return name; }

    public String getSubject(String name ){
        return subject + " " + name;
    }

    public String getTemplate(){
        return template.toString();
    }

    public String getSubject(long id, String nameService ){
        return subject + " #" + id + " para " + nameService;
    }

    public boolean isAboutAppointment(){ return isAboutAppointment; }
}
