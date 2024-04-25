package com.lucas.loginauthapi.controller;

import com.lucas.loginauthapi.user.domain.User;
import com.lucas.loginauthapi.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class TesteController {

    @Autowired
    private UserRepository repository;
    @GetMapping
    public ResponseEntity<List<User>> helloWorld(){
        var user = this.repository.findAll();
        return ResponseEntity.ok().body(user);
    }
}
