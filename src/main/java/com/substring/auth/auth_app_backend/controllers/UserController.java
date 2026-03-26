package com.substring.auth.auth_app_backend.controllers;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.User;
import com.substring.auth.auth_app_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    //create user api-> POST
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping
    //get all users api -> GET
    public ResponseEntity<Iterable<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    //get user by email
    @GetMapping("/email/{email}")
        public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
            return ResponseEntity.ok(userService.getUserByEmail(email));


    }






}
