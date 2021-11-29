package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.Game;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;

public interface GameRepository extends CrudRepository<Game, Long> {
    Game findByName(String name);
}