package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.Appointment;
import ar.edu.itba.paw.model.exceptions.AppointmentNonExistentException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("securityServiceImpl")
public class SecurityServiceImpl implements SecurityService{

    private final UserService userService;
    private final AppointmentService appointmentService;

    @Autowired
    public SecurityServiceImpl(AppointmentService appointmentService, UserService userService){
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Optional<String> getCurrentUserEmail() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            return Optional.of(context.getAuthentication().getName());
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUserAppointment(long appointmentId){
        User user =getCurrentUser().orElseThrow(UserNotFoundException::new);
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(AppointmentNonExistentException::new);
        return user.getUserId() == appointment.getUserid();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getCurrentUser() {
        final Optional<String> mayBeEmail = getCurrentUserEmail();
        if (!mayBeEmail.isPresent()){
            return Optional.empty();
        }
        return userService.findByEmail(mayBeEmail.get());
    }

}
