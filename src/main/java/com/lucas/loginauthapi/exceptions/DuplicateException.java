package com.lucas.loginauthapi.exceptions;

public class DuplicateException extends RuntimeException{
    
    public DuplicateException(){
        super("Este email já existe!");
    }

}
