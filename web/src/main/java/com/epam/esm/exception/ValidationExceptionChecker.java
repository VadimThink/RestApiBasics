package com.epam.esm.exception;

import org.springframework.validation.BindingResult;

import javax.validation.ValidationException;

public class ValidationExceptionChecker {

    public static void checkDtoValidation(BindingResult bindingResult){
        StringBuilder exceptionMessage = new StringBuilder();
        if (bindingResult.hasErrors()){
            if (bindingResult.hasFieldErrors("name")){
                exceptionMessage.append("Name not valid ");
            }
            if (bindingResult.hasFieldErrors("description")){
                exceptionMessage.append("Description not valid ");
            }
            if (bindingResult.hasFieldErrors("price")){
                exceptionMessage.append("Price not valid ");
            }
            if (bindingResult.hasFieldErrors("Duration")){
                exceptionMessage.append("Duration not valid ");
            }
            throw new ValidationException(exceptionMessage.toString());
        }
    }

}
