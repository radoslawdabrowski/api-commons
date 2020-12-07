package online.buildit.commons.locale.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.buildit.commons.locale.resolvers.CommonLocaleResolver;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class LocaleProcessingFilter extends OncePerRequestFilter {

    private final CommonLocaleResolver commonLocaleResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        commonLocaleResolver.setLocale(httpServletRequest, httpServletResponse, commonLocaleResolver.resolveLocale(httpServletRequest));
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
