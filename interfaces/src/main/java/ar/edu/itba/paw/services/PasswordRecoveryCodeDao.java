package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.PasswordRecoveryCode;

import java.util.Optional;
import java.util.UUID;

public interface PasswordRecoveryCodeDao {
    void saveCode(long userid, UUID code);
    void deleteCode(long userid);
    Optional<PasswordRecoveryCode> getCode(long userid);

    Optional<PasswordRecoveryCode> getCode(UUID code);
}
