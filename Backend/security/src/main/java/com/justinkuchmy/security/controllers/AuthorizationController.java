package com.justinkuchmy.security.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justinkuchmy.security.config.UserAuthenticationProvider;
import com.justinkuchmy.security.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/authorize")
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;
    
    @GetMapping("")
    public ResponseEntity<ArrayList<String>> authorizeUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        ArrayList<String> role = new ArrayList<String>();
        String token = null;
        String username = null;
        token = authHeader.substring(7);
        if(!token.equalsIgnoreCase("null"))
        {
            username = userAuthenticationProvider.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
            role.add(authority.getAuthority());
            }
        }
        
        return ResponseEntity.ok(role);
    }
    
}
