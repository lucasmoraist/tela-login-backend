package com.lucas.loginauthapi.user.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// Utilizado para fazer a injeção de dependência em todos atributos final criado
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ResponseDTO authLogin(LoginRequestDTO dto){
        User user = this.repository.findByEmail(dto.email()).orElseThrow(() -> new EmailNotFound("Email incorreto"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) { throw new PasswordException(); }

        return this.token(user);
    }

    public ResponseDTO authRegister(RegisterRequestDTO dto){
        Optional<User> optionalUser = this.repository.findByEmail(dto.email());

        if (optionalUser.isPresent()) { throw new DuplicateException(); }

        User newUser = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        this.repository.save(newUser);

        return this.token(newUser);
    }

    private ResponseDTO token(User user){
        String token = this.tokenService.generateToken(user);
        return new ResponseDTO(user.getEmail(), token);
    }

}
