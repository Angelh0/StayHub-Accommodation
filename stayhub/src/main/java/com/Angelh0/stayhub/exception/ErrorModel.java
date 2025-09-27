package com.Angelh0.stayhub.exception;

//Metodos de validación ordenados y explicados brevemente

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorModel { // 1. Define código y mensaje de cada uno de los errores

    private String code;
    private String message;
}
