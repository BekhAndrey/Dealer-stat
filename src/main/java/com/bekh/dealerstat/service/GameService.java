package com.bekh.dealerstat.service;

import com.bekh.dealerstat.model.Game;
import com.bekh.dealerstat.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gameService")
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll(){
        return  (List<Game>) gameRepository.findAll();
    }

    public Game findById(Long id) { return gameRepository.findById(id).orElse(null);}

    public void save(Game game) { gameRepository.save(game);}

    public void delete(Game game) { gameRepository.delete(game);}
}
