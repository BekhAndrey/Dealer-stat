package com.bekh.dealerstat.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private Set<GameObject> gameObjects;
}


