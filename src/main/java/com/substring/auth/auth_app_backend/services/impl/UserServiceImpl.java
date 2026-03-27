package com.substring.auth.auth_app_backend.services.impl;

import com.substring.auth.auth_app_backend.dtos.UserDto;
import com.substring.auth.auth_app_backend.entities.Provider;
import com.substring.auth.auth_app_backend.entities.User;
import com.substring.auth.auth_app_backend.exceptions.ResourceNotFoundException;
import com.substring.auth.auth_app_backend.helpers.UserHelper;
import com.substring.auth.auth_app_backend.repositories.UserRepository;
import com.substring.auth.auth_app_backend.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //user related save so, user repo is required

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if(userDto.getEmail() == null || userDto.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is Required");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new IllegalArgumentException("User with given email already exists");
        }

        //extra checks here
        //converting from dto to entity
        User user = modelMapper.map(userDto, User.class);

        //provider if not null, bring provider else Local will be there in default
        user.setProvider(userDto.getProvider()!=null ? userDto.getProvider() : Provider.LOCAL);

        //role assign -> to user (for authorization)

        //finally
        User savedUser = userRepository.save(user);


        //pele savedUser ko DB me save kie
        //phir, usko UserDto(jo return krna h yaha) usme convert kr die
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        //parse first
        UUID uId = UserHelper.parseUUID(userId);

        //fetch old user here first
        User existingUser = userRepository
                .findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User Id not found !!"));

        //updating the fields
        //we are not going to change email id for this project
        //remaining we are doing
        if(userDto.getName()!=null) existingUser.setName(userDto.getName());
        if(userDto.getImage()!=null) existingUser.setImage(userDto.getImage());
        if(userDto.getProvider()!=null) existingUser.setProvider(userDto.getProvider());

        //TODO change the password updation logic..
        //since with encode the password in backend(hashed)
        if(userDto.getPassword()!=null) existingUser.setPassword(userDto.getPassword());
        existingUser.setEnable(userDto.isEnable()); //for enable
        existingUser.setUpdatedAt(Instant.now());

        //save it now
        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);




    }

    @Override
    public UserDto getUserByEmail(String email) {
       User user =  userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with given email id !"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        //parsing string uid to UUID object
        UUID uId = UserHelper.parseUUID(userId);

        //going to repository and now delete
        User user = userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User Id not found !!"));
        userRepository.delete(user);

    }

    @Override
    public UserDto getUserById(String userId) {

        User user = userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(() -> new ResourceNotFoundException("User Id not found !!"));
        return modelMapper.map(user, UserDto.class);


    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList();
    }
}
