package com.dataart.springtraining.app.service;

import com.dataart.springtraining.app.model.User;
import org.springframework.security.core.Authentication;

/**
 * Created by mkim on 16/10/2015.
 */
public interface UserService {

    User createUser(String userName, String password);

    String createToken(String userName);

    void authenticateUser(String userName, String password);
}
