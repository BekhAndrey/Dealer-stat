package com.bekh.dealerstat.controller;
import com.bekh.dealerstat.model.Game;
import com.bekh.dealerstat.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    private String getGames(Model model){
        model.addAttribute("games", gameService.findAll());
        return "GamesPage";
    }

    @GetMapping("/add")
    private String createGame(Model model){
        model.addAttribute("game", new Game());
        return "NewGame";
    }

    @GetMapping("/{id}/edit")
    private String createGame(Model model, @PathVariable Long id){
        model.addAttribute("game", gameService.findById(id));
        return "EditGame";
    }

    @PostMapping("/add")
    private String addGame(@Valid @ModelAttribute("game") Game game, Errors errors){
        if(gameService.findByName(game.getName())!=null){
            errors.rejectValue("name", "name.NotUnique", "Game with such name already exists");
        }
        if(errors.hasErrors()){
            return "NewGame";
        }
        gameService.save(game);
        return "redirect:/games";
    }

    @PutMapping("/{id}/edit")
    private String updateGame(@Valid Game game, Errors errors ,@PathVariable Long id){
        if(gameService.findByName(game.getName())!=null){
            errors.rejectValue("name", "name.NotUnique", "Game with such name already exists");
        }
        if(errors.hasErrors()){
            return "EditGame";
        }
        Game gameToUpdate = gameService.findById(id);
        gameToUpdate.setName(game.getName());
        gameService.save(gameToUpdate);
        return "redirect:/games";
    }
}
