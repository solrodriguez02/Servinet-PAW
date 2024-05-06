package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.PasswordRecoveryCode;

import javax.mail.MessagingException;
import java.util.UUID;

public interface PasswordRecoveryCodeService {

    void sendCode(String email) throws MessagingException;
    PasswordRecoveryCode generateCode(long userid);
    void changePassword(UUID code, String newPassword) throws MessagingException;
    void deleteCode(long userid);
    boolean validateCode(UUID code);
}
