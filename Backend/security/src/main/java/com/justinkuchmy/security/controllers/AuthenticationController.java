package com.justinkuchmy.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justinkuchmy.security.config.UserAuthenticationProvider;
import com.justinkuchmy.security.dto.CredentialsDto;
import com.justinkuchmy.security.dto.SignUpDto;
import com.justinkuchmy.security.dto.UserDto;
import com.justinkuchmy.security.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
      @Autowired
    private UserService userService;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

     @PostMapping("/signIn")
    public String signIn(@RequestBody CredentialsDto authRequest) {

        var userDetails = userService.loadUserByUsername(authRequest.getLogin());
        var res = userAuthenticationProvider.generateToken(userDetails.getUsername());
        return res;
    }

     @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.signUp(user);
        return new ResponseEntity<UserDto>(createdUser, HttpStatus.OK);
    }
}
