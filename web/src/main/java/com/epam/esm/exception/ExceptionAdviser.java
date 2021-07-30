package com.epam.esm.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ValidationException;
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

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ExceptionInfo> handleDuplicateEntityException(DuplicateException e,Locale locale) {
        return buildErrorResponse(resolveResourceBundle(e.getMessage(), locale),
                40901, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidEntityException(
            MethodArgumentNotValidException e, Locale locale) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = e.getMessage();
        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
        }
        return buildErrorResponse(resolveResourceBundle(message, locale),
                40000, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<ExceptionInfo> handleInvalidParametersException(InvalidParametersException e,
                                                                          Locale locale) {
        return buildErrorResponse(resolveResourceBundle(e.getMessage(), locale),
                40001, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionInfo> handleNoSuchEntityException
            (MissingServletRequestParameterException e) {
        return buildErrorResponse(e.getMessage(), 40002, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ExceptionInfo> handleTypeMismatchException(TypeMismatchException e) {
        return buildErrorResponse(e.getMessage(), 40003, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ExceptionInfo> handleNumberFormatException(NumberFormatException e){
        return buildErrorResponse(e.getMessage(), 40003, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionInfo> handleNotReadableBodyException(Locale locale) {
        return buildErrorResponse(resolveResourceBundle("request.body.missing", locale),
                40004, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionInfo> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return buildErrorResponse(e.getMessage(), 40500, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionInfo> handleNoFoundException(NoHandlerFoundException e) {
        return buildErrorResponse(e.getMessage(), 40400, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<ExceptionInfo> handleNoSuchEntityException(NoSuchEntityException e,
                                                                     Locale locale) {
        return buildErrorResponse(resolveResourceBundle(e.getMessage(), locale),
                40401, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionInfo> handleOtherExceptions(Exception e) {
        return buildErrorResponse(e.getMessage(), 50000, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionInfo> handleValidationException(ValidationException e, Locale locale) {
        return buildErrorResponse(resolveResourceBundle(e.getMessage(), locale),
                40000, HttpStatus.BAD_REQUEST);
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
}
