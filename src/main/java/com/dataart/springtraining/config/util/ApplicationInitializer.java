package com.dataart.springtraining.config.util;

import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.User;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kimeshkov on 27.01.2016.
 */
@Component
public class ApplicationInitializer implements SmartInitializingSingleton {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void afterSingletonsInstantiated() {
        createUsers();
    }

    private void createUsers() {
        User user = new User();
        user.setLogin("Login");
        user.setName("Name");
        user.setPassword("Pass");

        usersRepository.save(user);
    }
}
