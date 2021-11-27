package com.bekh.dealerstat.controller;

import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.service.GameService;
import com.bekh.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class FirstController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return "redirect:sign-in";
    }

    @GetMapping("/sign-in")
    public String signInPage(Model model){
        model.addAttribute("user", new User());
        return "SignInPage";
    }

    @PostMapping("/sign-in")
    public String processSignIn(Model model, User user){
        User loggedUser = userService.findUserByEmail(user.getEmail());
        String result;
        if(loggedUser!=null && user.getPassword().equals(loggedUser.getPassword())){
            result="YES";
        } else result="No";
        model.addAttribute("result", result);
        model.addAttribute("role", loggedUser.getRole());
        return "ResultTest";
    }


    @GetMapping("/sign-up")
    public String sighUpPage(Model model){
        model.addAttribute("user", new User());
        return "SignUpPage";
    }

    @PostMapping("/sign-up")
    public String processSignUp(@ModelAttribute("user") User user){
        String result;
        if(userService.findUserByEmail(user.getEmail())!=null){
            return "redirect:sing-up";
        }
        userService.save(user);
        return "SignUpTest";
    }

    @GetMapping("/games")
    public String getGames(Model model){
        model.addAttribute("games", gameService.findAll());
        return "Games";
    }
}
