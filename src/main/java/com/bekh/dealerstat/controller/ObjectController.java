package com.bekh.dealerstat.controller;

import com.bekh.dealerstat.model.GameObject;
import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.model.UserRole;
import com.bekh.dealerstat.service.GameObjectService;
import com.bekh.dealerstat.service.GameService;
import com.bekh.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;


@Controller
@RequestMapping("/objects")
public class ObjectController {

    @Autowired
    private GameObjectService gameObjectService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @GetMapping()
    public String getAllObjects(Model model){
        model.addAttribute("users", userService.findAllByRole(UserRole.TRADER));
            return "game-object/AllGameObjects";
    }

    @GetMapping("/add")
    public String addObjectForm(@SessionAttribute("loggedUser") User user,
                                @RequestParam("gameId") Long gameId, Model model ){
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        } else if (user.getRole()!= UserRole.TRADER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the trader");
        } else {
            model.addAttribute("game", gameService.findById(gameId));
            model.addAttribute("object", new GameObject());
            return "game-object/NewGameObject";
        }
    }

    @PostMapping("/game/{id}")
    public String addObject(@SessionAttribute("loggedUser") User user,
                            @PathVariable("id") Long id, GameObject gameObject){
        gameObject.setAuthor(user);
        gameObject.setGame(gameService.findById(id));
        gameObjectService.save(gameObject);
        return "redirect:/objects/my";
    }

    @GetMapping("/my")
    public String getUserObjects(@SessionAttribute("loggedUser") User user , Model model ){
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        } else if (user.getRole()!= UserRole.TRADER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the trader");
        } else{
            model.addAttribute("games", gameService.findAll());
            model.addAttribute("userId", user.getId());
            return "game-object/UserObjects";
        }
    }

    @GetMapping("/{id}/edit")
    public String updateObjectForm(@PathVariable("id") Long objectId,
                                 @SessionAttribute("loggedUser") User user, Model model){
        GameObject gameObjectToUpdate = gameObjectService.findById(objectId);
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        } else if (!gameObjectToUpdate.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this object");
        } else {
            model.addAttribute("object", gameObjectToUpdate);
            return "game-object/EditGameObject";
        }
    }

    @PutMapping("/{id}")
    public String updateObject(@PathVariable("id") Long objectId, GameObject gameObject) {
        GameObject gameObjectToUpdate = gameObjectService.findById(objectId);
        gameObjectToUpdate.setTitle(gameObject.getTitle());
        gameObjectToUpdate.setUpdatedAt(LocalDate.now());
        gameObjectService.save(gameObjectToUpdate);
        return "redirect:/objects/my";
    }

    @GetMapping("/{id}/delete")
    public String deleteObjectForm(@PathVariable("id") Long objectId,
                                   @SessionAttribute("loggedUser") User user, Model model){
        GameObject gameObjectToDelete = gameObjectService.findById(objectId);
        if (user.getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorised");
        } else if (!gameObjectToDelete.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this object");
        } else {
            return "game-object/ConfirmDeleteObject";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteObject(@PathVariable("id") Long objectId){
        gameObjectService.delete(gameObjectService.findById(objectId));
        return "redirect:/objects/my";
    }
}