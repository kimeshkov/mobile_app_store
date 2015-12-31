package com.dataart.springtraining.app.controllers;

import com.dataart.springtraining.app.model.User;
import com.dataart.springtraining.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mkim on 16/10/2015.
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    @ResponseBody
    public User find(@PathVariable String login) {
        return userService.findUser(login);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        userService.createUser(user.getName(), user.getLogin(), user.getPassword());
    }

}
