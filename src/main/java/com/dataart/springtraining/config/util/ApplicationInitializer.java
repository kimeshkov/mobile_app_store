package com.dataart.springtraining.config.util;

import com.dataart.springtraining.app.dao.RoleRepository;
import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.Role;
import com.dataart.springtraining.app.model.User;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimeshkov on 27.01.2016.
 */
@Component
public class ApplicationInitializer implements SmartInitializingSingleton {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void afterSingletonsInstantiated() {
        createUsers();
    }

    private void createUsers() {
        User user = new User();
        user.setUsername("Name");
        user.setPassword(passwordEncoder.encode("Pass"));
        user.setRoles(createRoles("User"));
        usersRepository.save(user);
    }

    private List<Role> createRoles(String... roles) {
        List<Role> userRoles = new ArrayList<>(roles.length);
        for (String roleValue : roles) {
            Role role = new Role();
            role.setValue(roleValue);
            userRoles.add(role);
        }

        roleRepository.save(userRoles);

        return userRoles;
    }
}
