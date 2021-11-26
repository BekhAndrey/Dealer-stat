package com.bekh.dealerstat.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}


