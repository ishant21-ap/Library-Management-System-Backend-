package com.project.Library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice   //Using this we are giving advice to controller that whenever this kind of exception coming to controller handle it like this
public class ControllerExceptionAdvice{

    @ExceptionHandler(value = TxnException.class)
    public ResponseEntity<Object> handle(TxnException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
