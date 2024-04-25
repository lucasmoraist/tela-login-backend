package com.lucas.loginauthapi.exceptions;

public class PasswordException extends RuntimeException{

    public PasswordException(){}

    public PasswordException(String message){
        super(message);
    }
}
