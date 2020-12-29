package com.mycompany.safetyAlert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)    // Spring récupère le code Erreur
public class DataAlreadyExistException extends RuntimeException{
    public DataAlreadyExistException(String message){
        super(message);
    }

}
