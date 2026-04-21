package com.sistemareservau.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) //409 Error
public class ConflictException extends RuntimeException{
public ConflictException(String mensaje) {
        super(mensaje);
    }
}
