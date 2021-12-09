package com.bekh.dealerstat.controller;

import com.bekh.dealerstat.model.GameObject;
import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.model.UserRole;
import com.bekh.dealerstat.service.GameObjectService;
import com.bekh.dealerstat.service.GameService;
import com.bekh.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/objects")
public class ObjectController {

    private final GameObjectService gameObjectService;

    private final UserService userService;

    private final GameService gameService;

    public ObjectController(GameObjectService gameObjectService, UserService userService, GameService gameService) {
        this.gameObjectService = gameObjectService;
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping()
    public String getAllObjects(Model model) {
        model.addAttribute("users", userService.findAllByRole(UserRole.ROLE_TRADER));
        return "game-object/AllGameObjects";
    }

    @GetMapping("/add")
    public String addObjectForm(Model model) {
        model.addAttribute("gameObject", new GameObject());
        model.addAttribute("games", gameService.findAll());
        return "game-object/NewGameObject";
    }

    @PostMapping("/add")
    public String addObject(@Valid GameObject gameObject, String gameName,
                            Authentication authentication, Errors errors) {
        if (errors.hasErrors()) {
            return "game-object/NewGameObject";
        }
        gameObject.setAuthor(userService.findUserByEmail(authentication.getName()));
        gameObject.setGame(gameService.findByName(gameName));
        gameObjectService.save(gameObject);
        return "redirect:/objects/my";
    }

    @GetMapping("/my")
    public String getUserObjects(Model model, Authentication authentication) {
        model.addAttribute("games", gameService.findAll());
        model.addAttribute("userId", userService.findUserByEmail(authentication.getName()).getId());
        return "game-object/UserObjects";
    }

    @GetMapping("/{id}/edit")
    public String updateObjectForm(@PathVariable("id") Long objectId,
                                   Authentication authentication, Model model) {
        GameObject gameObjectToUpdate = gameObjectService.findById(objectId);
        if (!gameObjectToUpdate.getAuthor().getId()
                .equals(userService.findUserByEmail(authentication.getName()).getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this object");
        }
        model.addAttribute("gameObject", gameObjectToUpdate);
        return "game-object/EditGameObject";
    }

    @PutMapping("/{id}/edit")
    public String updateObject(@PathVariable("id") Long objectId,
                               @Valid GameObject gameObject, Errors errors) {
        if (errors.hasErrors()) {
            return "game-object/EditGameObject";
        }
        GameObject gameObjectToUpdate = gameObjectService.findById(objectId);
        gameObjectToUpdate.setTitle(gameObject.getTitle());
        gameObjectToUpdate.setUpdatedAt(LocalDate.now());
        gameObjectToUpdate.setApproved(false);
        gameObjectService.save(gameObjectToUpdate);
        return "redirect:/objects/my";
    }

    @GetMapping("/{id}/delete")
    public String deleteObjectForm(@PathVariable("id") Long objectId, Authentication authentication) {
        GameObject gameObjectToDelete = gameObjectService.findById(objectId);
        if (!gameObjectToDelete.getAuthor().getId()
                .equals(userService.findUserByEmail(authentication.getName()).getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this object");
        }
        return "game-object/ConfirmDeleteObject";
    }

    @DeleteMapping("/{id}")
    public String deleteObject(@PathVariable("id") Long objectId) {
        gameObjectService.delete(gameObjectService.findById(objectId));
        return "redirect:/objects/my";
    }
}