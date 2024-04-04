package com.seu.tms_mobile_banking.exception;

import com.seu.tms_mobile_banking.base.BaseError;
import com.seu.tms_mobile_banking.base.BasedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasedErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        BaseError<List<?>> basedError = new BaseError<>();
        List<Map<String, Object>> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("field", fieldError.getField());
                    error.put("reason", fieldError.getDefaultMessage());
                    errors.add(error);
                });
        basedError.setCode(HttpStatus.BAD_GATEWAY.getReasonPhrase());
        basedError.setDescription(errors);
        return new BasedErrorResponse(basedError);
    }
}

