package com.lucas.loginauthapi.user.controller;

import com.lucas.loginauthapi.exceptions.DuplicateException;
import com.lucas.loginauthapi.exceptions.EmailNotFound;
import com.lucas.loginauthapi.exceptions.PasswordException;
import com.lucas.loginauthapi.infra.security.TokenService;
import com.lucas.loginauthapi.user.domain.User;
import com.lucas.loginauthapi.user.dto.LoginRequestDTO;
import com.lucas.loginauthapi.user.dto.RegisterRequestDTO;
import com.lucas.loginauthapi.user.dto.ResponseDTO;
import com.lucas.loginauthapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
// Utilizado para fazer a injeção de dependência em todos atributos final criado
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        User user = this.repository.findByEmail(dto.email()).orElseThrow(() -> new EmailNotFound("Email incorreto"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) { throw new PasswordException(); }

        String token = this.tokenService.generateToken(user);
        return ResponseEntity.ok(new ResponseDTO(user.getEmail(), token));
    }

    @PostMapping("register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO dto) {
        Optional<User> optionalUser = this.repository.findByEmail(dto.email());
        
        if (optionalUser.isPresent()) { throw new DuplicateException(); }

        User newUser = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getEmail(), token));
    }
}
