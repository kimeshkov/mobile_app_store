package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<User, Integer> {

    //@Query("select u from User u where username = :userName")
    User findByUsername(String username);

}
