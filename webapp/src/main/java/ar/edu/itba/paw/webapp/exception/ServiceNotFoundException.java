package ar.edu.itba.paw.webapp.exception;

public class ServiceNotFoundException extends RuntimeException{
    //TODO: cambiar por llamado al constructor con parametros
    private static String servicename = "servicio 1";
    //TODO: ver cómo pasar el username si es que se ingresa desde una lista que ya fue cargada (más significativo que hablar de un id)
    private static final String message = "The service with id: %s was not found";


    public ServiceNotFoundException(String service){
        this.servicename = service;
    }
    public ServiceNotFoundException(){
    }

    @Override
    public String getMessage() {
        return String.format(message, servicename);
    }
}
