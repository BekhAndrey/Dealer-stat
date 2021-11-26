package com.bekh.dealerstat.service;


import com.bekh.dealerstat.model.Game;
import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email){ return userRepository.findUserByEmail(email); }

    public List<User> findAll(){
        return  (List<User>) userRepository.findAll();
    }

    public User findById(Long id) { return userRepository.findById(id).orElse(null);}

    public void save(User user) { userRepository.save(user);}

    public void delete(User user) { userRepository.delete(user);}
}
