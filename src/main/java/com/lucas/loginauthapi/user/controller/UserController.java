package com.lucas.loginauthapi.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.loginauthapi.user.domain.User;
import com.lucas.loginauthapi.user.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserRepository repository;
    @GetMapping
    public ResponseEntity<List<User>> helloWorld(){
        var user = this.repository.findAll();
        return ResponseEntity.ok().body(user);
    }

}
