package com.lucas.loginauthapi.exceptions;

public class EmailNotFound extends RuntimeException{
    
    public EmailNotFound(){
        super("User not found");
    }

    public EmailNotFound(String message){
        super(message);
    }

}
