package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.AppointmentInfo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface AppointmentDao {

    Optional<Appointment> findById(long id);

    Optional<List<Appointment>> getAllUpcomingServiceAppointments(long serviceid);

    Optional<List<Appointment>> getAllUpcomingServicesAppointments(Collection<Long> servicesIds, Boolean confirmed);

    Optional<List<AppointmentInfo>> getAllUpcomingUserAppointments(long userid, Boolean confirmed);

    Appointment create(long serviceid, long userid, LocalDateTime startDate, LocalDateTime endDate, String location);

    void confirmAppointment(long appointmentid);

    void cancelAppointment(long appointmentid);
}
