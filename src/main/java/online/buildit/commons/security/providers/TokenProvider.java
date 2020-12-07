package online.buildit.commons.security.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.buildit.commons.annotation.Provider;
import online.buildit.commons.security.UserPrincipal;
import online.buildit.commons.security.factory.AuthPropertiesFactory;
import org.springframework.security.core.Authentication;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * JWT Token Generate.
 */
@Slf4j
@Provider
@RequiredArgsConstructor
public class TokenProvider {

    private final AuthPropertiesFactory authPropertiesFactory;

    /**
     * Generating JWT token.
     * @param authentication authentication's data
     * @return JWT
     */
    public String generate(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
            .setSubject(String.valueOf(userPrincipal.getUuid()))
            .setIssuedAt(Date.from(ZonedDateTime.now(Clock.systemUTC()).toInstant()))
            .setExpiration(
                new Date(Date.from(ZonedDateTime.now(Clock.systemUTC()).toInstant()).getTime()
                    + authPropertiesFactory.getTokenExpirationMSec())
            )
            .signWith(getKey())
            .compact();
    }

    /**
     * Getting user uuid by token.
     * @param token token
     * @return user UUID
     */
    public UUID getUserIdFromToken(final String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(getKey())
            .parseClaimsJws(token)
            .getBody();

        return UUID.fromString(claims.getSubject());
    }

    /**
     * JWT Token Validation.
     * @param authToken token
     * @return true/false
     */
    public boolean validateToken(final String authToken) {
        Jwts.parser().setSigningKey(getKey()).parseClaimsJws(authToken);
        return true;
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(authPropertiesFactory.getTokenSecret().getBytes(StandardCharsets.UTF_8));
    }

}
