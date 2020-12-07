package online.buildit.commons.locale.resolvers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import online.buildit.commons.locale.enums.Characters;
import online.buildit.commons.locale.exceptions.AcceptLanguageHeaderIsBlankException;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@Primary
@Component
public class CommonLocaleResolver extends SessionLocaleResolver {

    @Value("headers.acceptLanguageHeader")
    public String ACCEPT_LANGUAGE_HEADER;

    @Override
    public Locale resolveLocale(final HttpServletRequest httpServletRequest) {
        final String language = getLanguageHeader(httpServletRequest);

        try {
            return LocaleUtils.toLocale(this.getLocaleName(language));
        } catch (final IllegalArgumentException | AcceptLanguageHeaderIsBlankException ex) {
            return Locale.getDefault();
        }
    }

    private String getLocaleName(@NonNull final String language) throws AcceptLanguageHeaderIsBlankException {
        if (StringUtils.isBlank(language)) {
            throw new AcceptLanguageHeaderIsBlankException();
        }
        return language.indexOf(Characters.COMMA) > 0?
            language.substring(0, language.indexOf(Characters.COMMA)):
            language.replace(Characters.HYPHEN, Characters.UNDERSCORE);
    }

    private String getLanguageHeader(final HttpServletRequest request) {
        return request.getHeader(ACCEPT_LANGUAGE_HEADER);
    }

    @Override
    protected Locale getDefaultLocale() {
        return Locale.UK;
    }

}
