package com.lucas.loginauthapi.user.controller;

import com.lucas.loginauthapi.user.dto.LoginRequestDTO;
import com.lucas.loginauthapi.user.dto.RegisterRequestDTO;
import com.lucas.loginauthapi.user.dto.ResponseDTO;
import com.lucas.loginauthapi.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        var response = this.service.authLogin(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        var response = this.service.authRegister(dto);
        return ResponseEntity.ok().body(response);
    }
}
