package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") String id){
        var findUserById = service.getUserById(id);
        return ResponseEntity.ok(findUserById);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){
        var listAll = service.findAllUsers();
        return ResponseEntity.ok(listAll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody UserDto dto){
        User updateUser = service.update(id, dto);
        return ResponseEntity.ok().body(updateUser);
    }
}
