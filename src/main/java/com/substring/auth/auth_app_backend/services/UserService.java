package com.substring.auth.auth_app_backend.services;

import com.substring.auth.auth_app_backend.dtos.UserDto;

public interface UserService {

    //create user
    UserDto createUser(UserDto userDto);

    //update user
    UserDto updateUser(UserDto userDto, String userId);

    //get user by email
    UserDto getUserByEmail(String email);

    //delete user
    void deleteUser(String userId);

    //get User by Id
    UserDto getUserById(String userId);

    //get all users
    Iterable<UserDto> getAllUsers();
}
