package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("securityServiceImpl")
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private UserDao userDao;

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
    public Optional<User> getCurrentUser() {
        final Optional<String> mayBeEmail = getCurrentUserEmail();
        if (!mayBeEmail.isPresent()){
            return Optional.empty();
        }
        return userDao.findByEmail(mayBeEmail.get());
    }

}
