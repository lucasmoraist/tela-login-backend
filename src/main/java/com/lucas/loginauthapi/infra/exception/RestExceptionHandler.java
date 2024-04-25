package com.lucas.loginauthapi.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lucas.loginauthapi.exceptions.DuplicateException;
import com.lucas.loginauthapi.exceptions.EmailNotFound;
import com.lucas.loginauthapi.exceptions.ExceptionDTO;
import com.lucas.loginauthapi.exceptions.PasswordException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(DuplicateException.class)
    private ResponseEntity<ExceptionDTO> threatDuplicateEntry(DuplicateException exception){
        ExceptionDTO dto = new ExceptionDTO("Este email já existe", HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(PasswordException.class)
    private ResponseEntity<ExceptionDTO> incorretCredentials(PasswordException exception){
        ExceptionDTO dto = new ExceptionDTO("Senha incorreta", HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(dto);
    }    

    @ExceptionHandler(EmailNotFound.class)
    private ResponseEntity<ExceptionDTO> userNotFound(EmailNotFound exception){
        ExceptionDTO dto = new ExceptionDTO("Email incorreto", HttpStatus.NOT_FOUND);
        return ResponseEntity.badRequest().body(dto);
    }
}
