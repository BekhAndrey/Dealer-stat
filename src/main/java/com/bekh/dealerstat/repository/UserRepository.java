package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByEmail(String email);
}
