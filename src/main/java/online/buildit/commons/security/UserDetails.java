package online.buildit.commons.security;

import java.util.UUID;

public interface UserDetails extends org.springframework.security.core.userdetails.UserDetails {

    UUID getUUID();
    String getEmail();

    @Override
    default String getUsername() {
        return getEmail();
    }

}
