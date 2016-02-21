package com.dataart.springtraining.app.controllers;

import com.dataart.springtraining.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mkim on 16/10/2015.
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public TokenTransfer authenticate(String username, String password) {
        return authenticateUser(username, password);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public UserTransfer getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof String && principal.equals("anonymousUser")) {
            throw new WebApplicationException(401);
        }

        UserDetails userDetails = (UserDetails) principal;

        return new UserTransfer(userDetails.getUsername(), getRoles(userDetails.getAuthorities()));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createUser(String username, String password) {
        userService.createUser(username, password);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "This username is not found")
    @ExceptionHandler(AuthenticationException.class)
    public void exceptionHandler() {
    }

    private TokenTransfer authenticateUser(String userName, String password) {
        userService.authenticateUser(userName, password);
        return new TokenTransfer(userService.createToken(userName));
    }

    private List<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = new ArrayList<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        return roles;
    }

    private static class TokenTransfer {
        private String token;

        public TokenTransfer(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private static class UserTransfer {
        private String name;
        private List<String> roles;

        public UserTransfer(String name, List<String> roles) {
            this.name = name;
            this.roles = roles;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

}
