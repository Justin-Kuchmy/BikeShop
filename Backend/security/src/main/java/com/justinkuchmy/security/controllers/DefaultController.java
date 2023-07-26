package com.justinkuchmy.security.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class DefaultController {
  @GetMapping
  public ResponseEntity<String> home() {
    return ResponseEntity.ok("Connected to localhost");
  }

}
