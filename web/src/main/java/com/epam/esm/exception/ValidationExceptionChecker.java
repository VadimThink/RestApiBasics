package com.epam.esm.exception;

import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

public class ValidationExceptionChecker {

    public static void checkDtoValidation(BindingResult bindingResult) {
        StringBuilder exceptionMessage = new StringBuilder();
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("name")) {
                exceptionMessage.append("validation.name");
            }
            if (bindingResult.hasFieldErrors("description")) {
                exceptionMessage.append("validation.description");
            }
            if (bindingResult.hasFieldErrors("price")) {
                exceptionMessage.append("validation.price");
            }
            if (bindingResult.hasFieldErrors("Duration")) {
                exceptionMessage.append("validation.duration");
            }
            throw new ValidationException(exceptionMessage.toString());
        }
    }

}
