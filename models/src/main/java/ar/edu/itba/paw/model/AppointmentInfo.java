package ar.edu.itba.paw.model;

import java.time.LocalDateTime;

public class AppointmentInfo extends BasicAppointment {
    private final String serviceName;
    private final String businessEmail;
    private final String businessTelephone;


    public AppointmentInfo(long id, long serviceid, LocalDateTime startDate, LocalDateTime endDate, String location, Boolean confirmed,
                           String serviceName, String businessEmail, String businessTelephone ) {
        super(id, serviceid, startDate, endDate, location, confirmed);
        this.serviceName = serviceName;
        this.businessEmail = businessEmail;
        this.businessTelephone = businessTelephone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public String getBusinessTelephone() {
        return businessTelephone;
    }
}
