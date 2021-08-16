package com.epam.esm.exception;

import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

public class ValidationExceptionChecker {

    public static void checkDtoValidation(BindingResult bindingResult) {
        String exceptionMessage = "";
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("name")) {
                exceptionMessage = "validation.name";
            }
            if (bindingResult.hasFieldErrors("description")) {
                exceptionMessage ="validation.description";
            }
            if (bindingResult.hasFieldErrors("price")) {
                exceptionMessage ="validation.price";
            }
            if (bindingResult.hasFieldErrors("duration")) {
                exceptionMessage = "validation.duration";
            }
            if (bindingResult.hasFieldErrors("userId")) {
                exceptionMessage = "validation.id";
            }
            if (bindingResult.hasFieldErrors("certificateId")) {
                exceptionMessage ="validation.id";
            }
            if (bindingResult.hasFieldErrors("login")){
                exceptionMessage ="validation.login";
            }
            throw new ValidationException(exceptionMessage);
        }
    }

}
