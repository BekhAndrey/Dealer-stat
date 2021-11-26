package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}