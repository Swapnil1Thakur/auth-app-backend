package com.substring.auth.auth_app_backend.services.impl;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.services.AuthService;
import com.substring.auth.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    //user ko register karana
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto registerUser(UserDto userDto) {

       //any logic here if you want before user registering
        //verify-> email or password, or roles etc

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); //ab password ko encode krke hi, user with password save hoga DB me

        return userService.createUser(userDto);

    }
}
