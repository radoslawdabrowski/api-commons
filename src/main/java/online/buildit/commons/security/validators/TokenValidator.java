package online.buildit.commons.security.validators;

import java.time.Clock;
import java.time.ZonedDateTime;

public class TokenValidator {

    public static boolean validate(final VerificationToken token, final VerificationType verificationType) {
        return verificationType.getName().equals(token.getName())
            && ZonedDateTime.now(Clock.systemUTC()).isBefore(token.getExpiresIn());
    }
}
