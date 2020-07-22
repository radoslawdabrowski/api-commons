package online.buildit.commons.resolvers;

import lombok.extern.slf4j.Slf4j;
import online.buildit.commons.enums.Characters;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@Primary
@Component
public class CommonLocaleResolver extends SessionLocaleResolver {

    @Override
    public Locale resolveLocale(final HttpServletRequest httpServletRequest) {
        final String language = httpServletRequest.getHeader("Accept-Language");

        if (!StringUtils.isBlank(language)) {
            try {
                if (language.indexOf(Characters.COMMA) > 0) {
                    return LocaleUtils.toLocale(language.substring(0, language.indexOf(Characters.COMMA)));
                } else {
                    return LocaleUtils.toLocale(language.replace(Characters.HYPHEN, Characters.UNDERSCORE));
                }
            } catch (final IllegalArgumentException ex) {
                return Locale.getDefault();
            }
        }

        return Locale.getDefault();
    }

    @Override
    protected Locale getDefaultLocale() {
        return Locale.UK;
    }

}
