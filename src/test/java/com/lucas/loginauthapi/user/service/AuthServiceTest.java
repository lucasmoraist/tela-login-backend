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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;

    @Autowired
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create user successfully when everthing is OK")
    void authRegisterCase1() {
        RegisterRequestDTO dto = new RegisterRequestDTO("Lucas", "lucsa@lucas.com", "123456789");
        User newUser = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .build();

        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(repository.save(any(User.class))).thenReturn(newUser);
        when(tokenService.generateToken(newUser)).thenReturn("generatedToken");

        ResponseDTO response = authService.authRegister(dto);

        assertNotNull(response);
        assertEquals(dto.email(), response.email());
        assertEquals("generatedToken", response.token());
        verify(repository, times(1)).findByEmail(dto.email());
        verify(repository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).generateToken(newUser);
    }

    @Test
    @DisplayName("Should throw Exception when email exist from BD")
    void authRegisterCase2() {
        RegisterRequestDTO dto = new RegisterRequestDTO("existing@example.com", "Existing User", "password123");
        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        assertThrows(DuplicateException.class, () -> authService.authRegister(dto));
        verify(repository, times(1)).findByEmail(dto.email());
        verify(repository, never()).save(any(User.class));
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should return ResponseDTO when login successful")
    void authLoginCase1() {

        LoginRequestDTO dto = new LoginRequestDTO("test@example.com", "password123");
        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.password(), user.getPassword())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("generatedToken");

        ResponseDTO response = authService.authLogin(dto);

        assertNotNull(response);
        assertEquals(dto.email(), response.email());
        assertEquals("generatedToken", response.token());
        verify(repository, times(1)).findByEmail(dto.email());
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    @DisplayName("Should throw EmailNotFoundException when email does not exist in the database")
    void authLoginCase2() {

        LoginRequestDTO dto = new LoginRequestDTO("nonexistent@example.com", "password123");
        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());

        EmailNotFound exception = assertThrows(EmailNotFound.class, () -> authService.authLogin(dto));
        assertEquals("Email incorreto", exception.getMessage());
        verify(repository, times(1)).findByEmail(dto.email());
        verify(tokenService, never()).generateToken(any(User.class));
    }

    @Test
    @DisplayName("Should throw PasswordException when password is incorrect")
    void authLoginCase3() {

        LoginRequestDTO dto = new LoginRequestDTO("test@example.com", "wrongPassword");
        User user = User.builder()
                .name("Test User")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(dto.password(), user.getPassword())).thenReturn(false);

        assertThrows(PasswordException.class, () -> authService.authLogin(dto));
        verify(repository, times(1)).findByEmail(dto.email());
        verify(tokenService, never()).generateToken(any(User.class));
    }

}