package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.User;
import com.dataart.springtraining.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mkim on 15/10/2015.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    @Override
    public int createUser(String username, String login, String password) {
        if (!isLoginAvailable(login)) {
            throw new IllegalArgumentException("The username is not available.");
        }

        User user = new User();
        user.setName(username);
        user.setLogin(login);
        user.setPassword(getEncryptedPassword(password));
        return usersRepository.save(user).getId();
    }

    private boolean isLoginAvailable(String login) {
        return usersRepository.findByLogin(login) == null;
    }

    private String getEncryptedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public User findUser(String userLogin) {
        return usersRepository.findByLogin(userLogin);
    }
}
