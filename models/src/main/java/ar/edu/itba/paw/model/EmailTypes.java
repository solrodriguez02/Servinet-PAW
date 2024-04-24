package ar.edu.itba.paw.model;

public enum EmailTypes {
    WAITING("WAITING","Creación de solicitud del turno"),
    REQUEST("REQUEST", "Solicitud del turno"),
    CANCELLED("CANCELLED","Cancelación del turno"),
    ACCEPTED("ACCEPTED", "Aceptación del turno"),
    DENIED("DENIED", "Rechazo del turno"),
    CREATED_SERVICE("CREATED", "Creación del servicio", false),
    DELETED_SERVICE("DELETED", "Eliminación del servicio", false),
    PASSWORD_RECOVER("RECOVER_PASSWORD", "Recuperación de contraseña", false),
    CONFIRM_NEW_PASSWORD("CONFIRM_NEW_PASSWORD", "Nueva contraseña definida con éxito", false);

    private final String name;
    private final String subject;
    private final Boolean isAboutAppointment;
    private final static String APPOINTMENT_TEMPLATE = "html/appointment.html";
    private final static String SERVICE_TEMPLATE = "html/service.html";
    private final static String RECOVER_PASSWORD_TEMPLATE = "html/recoverPassword.html";
    private final static String CONFIRM_NEW_PASSWORD_TEMPLATE = "html/confirmNewPassword.html";

    EmailTypes(String name, String subject) {
        this.name = name;
        this.subject = subject;
        this.isAboutAppointment = true;
    }

    EmailTypes(String name, String subject, Boolean isAboutAppointment) {
        this.name = name;
        this.subject = subject;
        this.isAboutAppointment = isAboutAppointment;
    }

    public String getType(){ return name; }

    public String getSubject(String nameService ){
        return subject + " " + nameService;
    }

    public String getTemplate(){
        return isAboutAppointment? APPOINTMENT_TEMPLATE: SERVICE_TEMPLATE;
    }

    public String getAppointmentTemplate(){
        return APPOINTMENT_TEMPLATE;
    };
    public String getRecoverPasswordTemplate(){
        return RECOVER_PASSWORD_TEMPLATE;
    }

    public String getConfirmNewPasswordTemplate(){
        return CONFIRM_NEW_PASSWORD_TEMPLATE;
    }
    public String getSubject(long id, String nameService ){
        return subject + " #" + id + " para " + nameService;
    }

    public boolean isAboutAppointment(){ return isAboutAppointment; }
}
