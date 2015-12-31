package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.model.User;

/**
 * Created by mkim on 16/10/2015.
 */
public interface UserService {

    int createUser(String username, String login, String password);

    User findUser(String userLogin);
}
