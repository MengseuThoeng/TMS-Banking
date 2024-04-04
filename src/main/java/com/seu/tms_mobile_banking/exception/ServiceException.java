package com.seu.tms_mobile_banking.exception;

import com.seu.tms_mobile_banking.base.BaseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class ServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleServiceErrors(ResponseStatusException ex){
        BaseError<String> baseError = new BaseError<>();
        baseError.setCode(ex.getStatusCode().toString());
        baseError.setDescription(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(baseError);
    }
}
