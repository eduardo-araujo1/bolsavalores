package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDto userDto){
        User newUser = new User(userDto);
        return userRepository.save(newUser);

    }

}
