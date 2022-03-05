package com.example.meritstack.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class MeritExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private Error processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        Error error = new Error();
        for (FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(Calendar.getInstance().getTime());
        error.setMessage("Validation failed");
        return error;
    }

    @Data
    static class Error {
        private Date timestamp;
        private int status;
        private String message;
        private String path;

        private List<FieldError> errors = new ArrayList<>();

        public void addFieldError(String objectName, String field, String defaultMessage) {
            FieldError error = new FieldError(objectName, field,defaultMessage);
            errors.add(error);
        }
    }
}
