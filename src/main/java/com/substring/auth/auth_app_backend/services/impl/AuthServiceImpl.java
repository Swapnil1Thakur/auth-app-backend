package com.substring.auth.auth_app_backend.services.impl;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.services.AuthService;
import com.substring.auth.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    //user ko register karana
    private final UserService userService;


    @Override
    public UserDto registerUser(UserDto userDto) {

       //any logic here if you want before user registering
        //verify-> email or password, or roles etc

        UserDto userDto1 = userService.createUser(userDto);
        return userDto1;
    }
}
