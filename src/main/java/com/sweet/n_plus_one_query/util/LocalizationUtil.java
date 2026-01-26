package com.sweet.n_plus_one_query.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class LocalizationUtil {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver; // resolve region to get language

    public String getLocalizedMessage(String key, Object... args){
        HttpServletRequest httpServletRequest = WebUtil.getCurrentRequest();
        Locale locale = localeResolver.resolveLocale(httpServletRequest);
        return messageSource.getMessage(key, args, locale);
    }

    public String getLocalMessage(String key, Object... args){
        return messageSource.getMessage(key, args, new Locale("vi"));
    }
}
