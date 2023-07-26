package com.justinkuchmy.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justinkuchmy.security.dto.UserDto;
import com.justinkuchmy.security.services.UserService;

@RestController
@RequestMapping("/api/v1/User")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{User_Email}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("User_Email") String email)
    {
        UserDto UserInfo = userService.LoadUserByLogin(email);
        return ResponseEntity.ok(UserInfo);
    }
    
}