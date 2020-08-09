package com.doodle.pollservice.handler;

import com.doodle.pollservice.service.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.lang.String.format;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException runtimeException, WebRequest webRequest) {
        return handleExceptionInternal(
                runtimeException,
                format("Conflict in request: [%s]", runtimeException.getMessage()),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                webRequest);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException runtimeException, WebRequest webRequest) {
        return handleExceptionInternal(
                runtimeException,
                format("Object not found: [%s]", runtimeException.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                webRequest);
    }

}
