package com.dataart.springtraining.app.service;

import org.springframework.security.core.Authentication;

/**
 * Created by mkim on 16/10/2015.
 */
public interface UserService {

    int createUser(String userName, String password);

    String createToken(String userName);

    Authentication authenticateUser(String userName, String password);
}
