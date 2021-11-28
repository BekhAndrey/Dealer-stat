package com.bekh.dealerstat.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First Name cannot be empty.")
    @Size(min = 2, max = 25, message = "First Name cannot be less than 2 and more than 25 symbols")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last Name cannot be empty.")
    @Size(min = 2, max = 25, message = "Last Name cannot be less than 2 and more than 25 symbols")
    private String lastName;

    @Email(message = "Invalid email template.")
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password cannot be less than 8 symbols")
    private String password;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<GameObject> gameObjects;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}

