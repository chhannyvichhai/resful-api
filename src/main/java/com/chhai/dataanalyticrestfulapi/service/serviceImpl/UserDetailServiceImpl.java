package com.chhai.dataanalyticrestfulapi.service.serviceImpl;

import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    // load user from the database
    // inject repository
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User authenticatedUser = userRepository.findUserByName(username).stream().findFirst().orElse(null);
        System.out.println("Here is the authenticatedUser : " + authenticatedUser);

        if (authenticatedUser==null){
            throw new UsernameNotFoundException("Authenticated User doesn't exist");
        }
        // Create an object of UserDetails
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)
                org.springframework.security.core.userdetails.User.builder()
                .username(authenticatedUser.getUsername())
                .password(authenticatedUser.getPassword())
                .roles("USER").build();

        return user;
    }
}
