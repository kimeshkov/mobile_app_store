package com.dataart.springtraining.app.controllers;

import com.dataart.springtraining.app.model.User;
import com.dataart.springtraining.app.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by mkim on 16/10/2015.
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public LoginResponse login(@RequestBody UserLogin userLogin) {
        Authentication authentication = userService.authenticateUser(userLogin.name, userLogin.password);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        LoginResponse response = new LoginResponse();
        response.setUserName(userDetails.getUsername());
        response.setRoles(getRoles(userDetails.getAuthorities()));
        response.setToken(userService.createToken(userDetails.getUsername()));

        return response;
    }

    private List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = new ArrayList<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        userService.createUser(user.getUsername(), user.getPassword());
    }

    private static class UserLogin {
        public String name;
        public String password;
    }

    private static class LoginResponse {
        private String userName;
        private String token;
        private List<String> roles;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

}
