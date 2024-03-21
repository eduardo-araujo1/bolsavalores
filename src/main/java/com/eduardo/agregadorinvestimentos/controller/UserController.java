package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        User newUser = service.createUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userID){
        return null;
    }
}
