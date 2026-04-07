package com.substring.auth.auth_app_backend.controllers;

import com.substring.auth.auth_app_backend.dtos.LoginRequest;
import com.substring.auth.auth_app_backend.dtos.TokenResponse;
import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;


    //token generate kr rha hoga ye
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest loginRequest
    ){
        //authenticate the user first
        authenticate(loginRequest);
    }

    //function to authenticate a user
    private Authentication authenticate(LoginRequest loginRequest) {
        try{
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(),loginRequest.password()));
        }catch (Exception e){
            throw new BadCredentialsException("Invalid Username and Password!!");
        }

    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userDto));
    }
}
