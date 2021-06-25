package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ExceptionAdviser {
    private static final List<String> AVAILABLE_LOCALES = Arrays.asList("en_US", "ru_RU");
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private final ResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public ExceptionAdviser(ResourceBundleMessageSource bundleMessageSource) {
        this.bundleMessageSource = bundleMessageSource;
    }

    private ResponseEntity<ExceptionInfo> buildErrorResponse(String message, int code,
                                                                 HttpStatus status) {
        ExceptionInfo response = new ExceptionInfo(message, code);
        return new ResponseEntity<>(response, status);
    }

    private String resolveResourceBundle(String key, Locale locale) {
        if (!AVAILABLE_LOCALES.contains(locale.toString())) {
            locale = DEFAULT_LOCALE;
        }
        return bundleMessageSource.getMessage(key, null, locale);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ExceptionInfo> handleDuplicateException(
            DuplicateException e, Locale locale) {
        return buildErrorResponse(resolveResourceBundle(e.getMessage(), locale),
                40999, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ExceptionInfo> handleNoSuchEntityException(NoSuchEntityException e,
                                                                         Locale locale) {
        return buildErrorResponse(resolveResourceBundle(e.getMessage(), locale),
                40444, HttpStatus.NOT_FOUND);
    }
}
