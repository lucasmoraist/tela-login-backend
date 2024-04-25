package com.lucas.loginauthapi.user.repository;

import com.lucas.loginauthapi.user.domain.User;
import com.lucas.loginauthapi.user.dto.RegisterRequestDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    // Utilizando o entity manager para popular o BD
    @Autowired
    private EntityManager entityManager;

    // Devemos partir do principio em que o JPA já testou os métodos criados com a sintaxe deles
    // Devemos testar apenas os métodos criados com query, porque nós que estamos desenvolvendo a lógica
    @Test
    @DisplayName("Should get User successfully from DB")
    void findByEmailSuccess() {
        // 1º Foi feito a criação do usuário
        String email = "lucas@lucas.com";
        RegisterRequestDTO dto = new RegisterRequestDTO("Lucas", email, "123456789");
        this.createUser(dto);

        // 2º Foi feito a busca pelo email
        Optional<User> foundedUser = this.repository.findByEmail(email);

        // 3º Foi verificado se o email existia no BD
        assertThat(foundedUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when user not exist")
    void findByEmailError() {
        String email = "lucas@lucas.com";
        // 1º Foi feito a busca de um email no BD
        Optional<User> foundedUser = this.repository.findByEmail(email);

        // 2º Foi verificado se o email não existia no BD
        assertThat(foundedUser.isPresent()).isFalse();
    }

    private User createUser(RegisterRequestDTO dto){
        User newUser = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .build();

        this.entityManager.persist(newUser);
        return newUser;
    }
}