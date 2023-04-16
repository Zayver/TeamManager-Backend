package com.jnz.teamManager.exception.controller;

import com.jnz.teamManager.exception.error.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.jnz.teamManager.exception.dto.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    protected ResponseEntity<Object>  userNameAlreadyExists(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.CONFLICT.value())
                .message("El usuario ya existe en el sistema").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    protected ResponseEntity<Object> emailAlreadyExists(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.CONFLICT.value())
                .message("El correo ya existe en el sistema").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {UserNotExistsException.class})
    protected ResponseEntity<Object> userNotExists(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value())
                .message("El usuario especificado no existe").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {RequestAlreadyExistsException.class})
    protected ResponseEntity<Object> requestExists(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.CONFLICT.value())
                .message("Ya has realizado una petición a este equipo").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {InvitationAlreadyExistsException.class})
    protected ResponseEntity<Object> invitationExists(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.CONFLICT.value())
                .message("Ya has invitado al usuario a ese equipo").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


}
