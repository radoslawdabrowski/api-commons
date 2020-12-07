package online.buildit.commons.security.factory;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.security")
public class AuthPropertiesFactory {

    private String tokenSecret;
    private long tokenExpirationMSec;

}
