package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);
}
