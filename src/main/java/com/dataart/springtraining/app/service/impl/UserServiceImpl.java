package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.User;
import com.dataart.springtraining.app.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private String jwtTokenSecret;

    @Transactional
    @Override
    public User createUser(String userName, String password) {
        if (!isUserNameAvailable(userName)) {
            throw new IllegalArgumentException("The username is not available.");
        }

        User user = new User();
        user.setUsername(userName);
        user.setPassword(getEncryptedPassword(password));
        return usersRepository.save(user);
    }

    private boolean isUserNameAvailable(String userName) {
        return usersRepository.findByUsername(userName) == null;
    }

    private String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String createToken(String userName) {
        return Jwts.builder().setSubject(userName).signWith(SignatureAlgorithm.HS512, jwtTokenSecret).compact();
    }

    @Override
    public void authenticateUser(String userName, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
