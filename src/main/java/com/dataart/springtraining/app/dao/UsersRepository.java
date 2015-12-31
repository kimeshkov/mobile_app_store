package com.dataart.springtraining.app.dao;

import com.dataart.springtraining.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mkim on 14/10/2015.
 */
public interface UsersRepository extends CrudRepository<User, Integer> {

    @Query("select u from User u where login = :login")
    User findByLogin(@Param("login") String login);

}