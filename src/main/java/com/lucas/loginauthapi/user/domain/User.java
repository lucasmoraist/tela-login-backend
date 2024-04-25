package com.lucas.loginauthapi.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Table(name = "users")
@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Min(3)
    @Column(nullable = false, length = 120)
    private String name;
    @Min(6)
    @Column(nullable = false, unique = true)
    private String email;
    @Min(6)
    @Column(nullable = false)
    private String password;

}
