package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<User> getUserById(String id){
        var user = userRepository.findById(UUID.fromString(id));
        if (!user.isPresent()){
            throw new RuntimeException("Usuário não pode ser encontrado ou não existe.");
        }
        return user;
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(String id){
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);
        userRepository.delete(user);
    }

    public User update(String id, UserDto userDto){
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);

        if (!userDto.password().isEmpty()) user.setPassword(userDto.password());
        if (!userDto.email().isEmpty()) user.setEmail(userDto.email());
        if (!userDto.username().isEmpty()) user.setUsername(userDto.username());

        return userRepository.save(user);
    }

}
