package com.bekh.dealerstat.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be empty.")
    private String name;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GameObject> gameObjects;
}


