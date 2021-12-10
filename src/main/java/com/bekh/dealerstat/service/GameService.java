package com.bekh.dealerstat.service;

import com.bekh.dealerstat.model.Game;
import com.bekh.dealerstat.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("gameService")
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll(){
        return  (List<Game>) gameRepository.findAll();
    }

    public Game findById(Long id) { return gameRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find the resource"));}

    public void save(Game game) { gameRepository.save(game);}

    public void delete(Game game) { gameRepository.delete(game);}

    public Game findByName(String name) { return gameRepository.findByName(name);}
}
