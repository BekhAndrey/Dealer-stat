package com.bekh.dealerstat.controller;

import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.service.GameService;
import com.bekh.dealerstat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@SessionAttributes({"loggedUser"})
public class LoginController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @ModelAttribute("loggedUser")
    public User setUpUserForm() {
        return new User();
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signInPage(Model model){
        model.addAttribute("user", new User());
        return "SignInPage";
    }

    @PostMapping("/sign-in")
    public String processSignIn(Model model, User user, Errors errors){
        if(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())==null){
            errors.reject("user.NotFound");
            return "SignInPage";
        }
        model.addAttribute("loggedUser", userService.findUserByEmail(user.getEmail()));
        return "ResultTest";
    }


    @GetMapping("/sign-up")
    public String sighUpPage(Model model){
        model.addAttribute("user", new User());
        return "SignUpPage";
    }

    @PostMapping("/sign-up")
    public String processSignUp(@Valid User user, Errors errors){
        if(userService.findUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email", "email.notUnique", "This email is already in use.");
        }
        if(errors.hasErrors()){
            return "SignUpPage";
        }
        userService.save(user);
        return "SignUpTest";
    }
}
