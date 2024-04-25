package com.lucas.loginauthapi.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(String message, HttpStatus status) {}
