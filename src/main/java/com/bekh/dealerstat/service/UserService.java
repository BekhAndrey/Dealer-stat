package com.bekh.dealerstat.service;


import com.bekh.dealerstat.model.Game;
import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find the resource"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

}
