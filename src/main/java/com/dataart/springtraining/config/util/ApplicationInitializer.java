package com.dataart.springtraining.config.util;

import com.dataart.springtraining.app.dao.RoleRepository;
import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.Role;
import com.dataart.springtraining.app.model.User;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimeshkov on 27.01.2016.
 */
@Component
public class ApplicationInitializer implements SmartInitializingSingleton {

    private Path imagePath = Paths.get("images");
    private Path filePath = Paths.get("zip_files");


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private String fileStoreRootPath;

    @Override
    public void afterSingletonsInstantiated() {
        clearFileStore();
        createUsers();
    }

    private void clearFileStore() {
        Path root = Paths.get(fileStoreRootPath);
        Path images = root.resolve(imagePath);
        Path files = root.resolve(filePath);

        deleteDir(images);
        deleteDir(files);
    }

    private void deleteDir(Path images) {
        try {
            Files.walkFileTree(images, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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
