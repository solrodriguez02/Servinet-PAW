package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.PasswordRecoveryCode;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PasswordRecoveryCodeDao {
    PasswordRecoveryCode saveCode(long userid, UUID code, LocalDateTime ExpirationDate);
    void deleteCode(long userid);
    Optional<PasswordRecoveryCode> getCodeByUserId(long userid);

    Optional<PasswordRecoveryCode> getCodeByUUID(UUID code);
}
