package com.jnz.teamManager.exception.controller;

import com.jnz.teamManager.exception.dto.ErrorResponse;
import com.jnz.teamManager.exception.error.*;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
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

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> badCredentials(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.UNAUTHORIZED.value())
                .message("Usuario o contraseña incorrectos").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> unknownException(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error desconocido en el servidor").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = TeamNotExistsException.class)
    protected ResponseEntity<Object> teamNotExists(RuntimeException ex, WebRequest request){
        val body = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value())
                .message("Equipo no existente").build();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }




}
