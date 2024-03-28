package com.eduardo.agregadorinvestimentos.controller;

import com.eduardo.agregadorinvestimentos.dto.AccountReponseDto;
import com.eduardo.agregadorinvestimentos.dto.CreateAccountDto;
import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "Api para gerenciamento de usuarios")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Registra um novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    })

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        User newUser = service.createUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUser.getUsername()).toUri();
        return ResponseEntity.created(uri).body(newUser);
    }

    @Operation(summary = "Busca um usuário pelo id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") String id){
        var findUserById = service.getUserById(id);
        return ResponseEntity.ok(findUserById);
    }

    @Operation(summary = "Lista de usuários", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com todos usuarios cadastrados")
    })

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){
        var listAll = service.findAllUsers();
        return ResponseEntity.ok(listAll);
    }

    @Operation(summary = "Deletar um usuário", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") String id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar usuário", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody UserDto dto){
        User updateUser = service.update(id, dto);
        return ResponseEntity.ok().body(updateUser);
    }

    @Operation(summary = "Criar conta para usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })

    @PostMapping("/{id}/accounts")
    public ResponseEntity<User> createAccount(@PathVariable("id") String id, @RequestBody CreateAccountDto accountDto) {
        service.createAccount(id, accountDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar contas do usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contas do usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountReponseDto>> listAccounts(@PathVariable("id") String userId) {
        List<AccountReponseDto> accounts = service.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
}
