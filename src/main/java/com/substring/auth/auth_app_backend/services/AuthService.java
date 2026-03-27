package com.substring.auth.auth_app_backend.services;

import com.substring.auth.auth_app_backend.dtos.UserDto;

public interface AuthService {

    //for register
    UserDto registerUser(UserDto userDto);

    //login user

}
