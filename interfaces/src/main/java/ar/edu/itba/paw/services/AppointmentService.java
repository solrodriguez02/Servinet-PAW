package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    Optional<Appointment> findById(long id);

    Optional<List<Appointment>> getAllUpcomingServiceAppointments(long serviceid);

    Optional<List<Appointment>> getAllUpcomingServicesAppointments(Collection<Long> serviceIds, Boolean confirmed);

    Optional<List<AppointmentInfo>> getAllUpcomingUserAppointments(long userid, Boolean confirmed);

    Appointment create(long serviceid, String name, String surname, String email, String location, String telephone, String date);

    long confirmAppointment(long appointmentid);

    long cancelAppointment(long appointmentid);

    long denyAppointment(long appointmentid);

}
