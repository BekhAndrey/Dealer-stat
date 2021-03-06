package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.User;
import com.bekh.dealerstat.model.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    List<User> findAllByRole(UserRole role);
}
