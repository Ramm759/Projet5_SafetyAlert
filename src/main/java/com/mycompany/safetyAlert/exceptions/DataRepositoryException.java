package com.mycompany.safetyAlert.exceptions;

import com.mycompany.safetyAlert.repository.DataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataRepositoryException extends RuntimeException{
    public DataRepositoryException (String message, Throwable cause){
        super(message, cause);
    }
}
