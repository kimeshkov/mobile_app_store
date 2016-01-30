package com.dataart.springtraining.app.service.impl;

import com.dataart.springtraining.app.dao.UsersRepository;
import com.dataart.springtraining.app.model.Role;
import com.dataart.springtraining.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mkim on 14/10/2015.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = usersRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("The user with name " + userName + " was not found");
        }
        return new UserDetailsWrapper(user);
    }

    public static class UserDetailsWrapper implements UserDetails {
        private final User user;

        public UserDetailsWrapper(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getValue()));
            }
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
