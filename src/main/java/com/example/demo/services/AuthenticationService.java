package com.example.demo.services;

import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository repository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(RegisterUserDto registerUserDto){
        User user = new User();
        user.setFullName(registerUserDto.getFullName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        return repository.save(user);
    }

    public User authenticate(LoginUserDto userDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword())
        );

        return repository.findByEmail(userDto.getEmail()).orElseThrow();
    }

}
