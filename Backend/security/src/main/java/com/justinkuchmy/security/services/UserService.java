package com.justinkuchmy.security.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import com.justinkuchmy.security.dto.*;
import com.justinkuchmy.security.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService{
     @Autowired 
    private UserRepository userRepo;

        
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDto signUp(SignUpDto newUser) {
        var user = UserDto.builder()
        .first_name(newUser.getFirstName())
        .last_name(newUser.getLastName())
        .email(newUser.getEmail())
        .hashword(passwordEncoder().encode(newUser.getPassword()))
        .role(Role.USER)
        .build();
        userRepo.save(user);
        return user;
    }

    public void signOut(UserDto user) {
        // nothing to do at the moment
    }


    public UserDto LoadUserByLogin(String email) {
       var userInfo = userRepo.findByEmail(email).get();
        return userInfo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
       Optional<UserDto> userInfo = userRepo.findByEmail(username);
       return new UserInfoUserDetails(userInfo.get());
    }
}
