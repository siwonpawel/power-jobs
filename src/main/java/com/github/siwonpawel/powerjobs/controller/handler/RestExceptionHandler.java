package com.github.siwonpawel.powerjobs.controller.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.ResourceBundle;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final String RESOURCEBUNDLE_REST_ERRORS = "resourcebundle.restErrors";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> notFound() {
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.restErrors", LocaleContextHolder.getLocale());
        return new ResponseEntity<>(bundle.getString("rest.error.data-not-found"), HttpStatus.NOT_FOUND);
    }

}
