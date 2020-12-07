package online.buildit.commons.security;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
public class UserPrincipal {

    private UUID uuid;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public static <USER extends UserDetails> UserPrincipal create(final USER user) {
        return create(user, Collections.emptyList());
    }

    /**
     * Creating user principal object.
     * @param user model user
     * @param authorities user's roles
     * @return {@link UserPrincipal}
     */
    public static <USER extends UserDetails> UserPrincipal create(final USER user,
                                       final List<? extends GrantedAuthority> authorities) {
        return UserPrincipal.builder()
            .uuid(user.getUUID())
            .email(user.getEmail())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
    }

}

