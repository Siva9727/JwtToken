package com.example.demo.controllers;

import com.example.demo.dtos.LoginResponse;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.entities.User;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto userDto){
        User user = service.signUp(userDto);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto userDto){
        User authenticate = service.authenticate(userDto);
        String token = jwtService.generateToken(authenticate);
        LoginResponse response = new LoginResponse(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(response);
    }

    }
