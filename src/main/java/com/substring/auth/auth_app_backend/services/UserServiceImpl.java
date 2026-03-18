package com.substring.auth.auth_app_backend.services;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.Provider;
import com.substring.auth.auth_app_backend.entities.User;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    //user related save so, user repo is required
    @Autowired
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {

        if(userDto.getEmail() == null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is Required");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }

        //extra checks here
        //converting from dto to entity
        User user = modelMapper.map(userDto, User.class);

        //provider if not null, bring provider else Local will be there in default
        userDto.setProvider(userDto.getProvider()!=null ? userDto.getProvider() : Provider.LOCAL);

        //role assign -> to user (for authorization)

        //finally
        User savedUser = userRepository.save(user);


        //pele savedUser ko DB me save kie
        //phir, usko UserDto(jo return krna h yaha) usme convert kr die
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserDto getUserById(String userId) {
        return null;
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return null;
    }
}
