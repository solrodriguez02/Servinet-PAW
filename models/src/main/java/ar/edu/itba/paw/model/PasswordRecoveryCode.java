package ar.edu.itba.paw.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PasswordRecoveryCode {
    private long userid;
    private UUID code;
    private LocalDateTime expirationDate;

    public PasswordRecoveryCode(long userid, UUID code, LocalDateTime expirationDate) {
        this.userid = userid;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public long getUserId() {
        return userid;
    }

    public UUID getCode() {
        return code;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
