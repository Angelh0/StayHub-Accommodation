package com.Angelh0.stayhub.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private List<ErrorModel> errors; //listado de errores

    public BusinessException(List<ErrorModel> errors) {  // Excepcion personalizada para BusinessErrors
        super("Business exception occurred");
        this.errors = errors;
    }

    public BusinessException(String cityNotFound) {

    }
}