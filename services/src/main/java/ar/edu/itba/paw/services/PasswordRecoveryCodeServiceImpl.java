package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.PasswordRecoveryCode;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordRecoveryCodeServiceImpl implements PasswordRecoveryCodeService{
    private final PasswordRecoveryCodeDao passwordRecoveryCodeDao;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public PasswordRecoveryCodeServiceImpl(PasswordRecoveryCodeDao passwordRecoveryCodeDao, EmailService emailService, UserService userService){
        this.passwordRecoveryCodeDao = passwordRecoveryCodeDao;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void sendCode(String email) throws MessagingException {
        User user = userService.findByEmail(email).get();
        PasswordRecoveryCode passwordRecoveryCode = generateCode(user.getUserId());
        emailService.recoverPassword(user, passwordRecoveryCode);
    }

    @Transactional
    @Override
    public PasswordRecoveryCode generateCode(long userid) {
        passwordRecoveryCodeDao.deleteCode(userid);
        UUID newCode = UUID.randomUUID();
        passwordRecoveryCodeDao.saveCode(userid, newCode);
        return new PasswordRecoveryCode(userid, newCode, LocalDateTime.now().plusHours(1));
    }

    @Transactional
    @Override
    public boolean validateCode(UUID code) {
        //User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasswordRecoveryCode passwordRecoveryCode = passwordRecoveryCodeDao.getCode(code).orElse(null);
        if (passwordRecoveryCode == null){
            return false;
        }
        return passwordRecoveryCode.getCode().equals(code);
    }

    @Transactional
    public void changePassword(UUID code, String newPassword) throws MessagingException{
        PasswordRecoveryCode passwordRecoveryCode = passwordRecoveryCodeDao.getCode(code).orElse(null);
        if (passwordRecoveryCode == null){
            return;
        }
        User user = userService.findById(passwordRecoveryCode.getUserId()).get();
        userService.changePassword(user.getEmail(), newPassword);
        deleteCode(passwordRecoveryCode.getUserId());
        emailService.confirmNewPassword(user);
    }

    @Transactional
    @Override
    public void deleteCode(long userid) {
        passwordRecoveryCodeDao.deleteCode(userid);
    }

}
