package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.dto.AccountReponseDto;
import com.eduardo.agregadorinvestimentos.dto.CreateAccountDto;
import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.Account;
import com.eduardo.agregadorinvestimentos.entity.BillingAddress;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.exception.UserNotFoundException;
import com.eduardo.agregadorinvestimentos.repository.AccountRepository;
import com.eduardo.agregadorinvestimentos.repository.BillingAdressRepository;
import com.eduardo.agregadorinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAdressRepository billingAdressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAdressRepository billingAdressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAdressRepository = billingAdressRepository;
    }

    public User createUser(UserDto userDto) {
        User newUser = new User(userDto);
        return userRepository.save(newUser);

    }

    public User getUserById(String id) {
        return getUserByIdOrThrow(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        User user = getUserByIdOrThrow(id);
        userRepository.delete(user);
    }

    public User update(String id, UserDto userDto) {
        User user = getUserByIdOrThrow(id);

        if (!userDto.password().isEmpty()) user.setPassword(userDto.password());
        if (!userDto.email().isEmpty()) user.setEmail(userDto.email());
        if (!userDto.username().isEmpty()) user.setUsername(userDto.username());

        return userRepository.save(user);
    }

    public void createAccount(String id, CreateAccountDto accountDto) {
        User user = getUserByIdOrThrow(id);

        Account account = createAccountFromDto(user, accountDto);
        Account savedAccount = accountRepository.save(account);

        BillingAddress billingAddress = createBillingAddressFromDto(savedAccount, accountDto);
        billingAdressRepository.save(billingAddress);
    }

    private Account createAccountFromDto(User user, CreateAccountDto accountDto) {
        return new Account(
                UUID.randomUUID(),
                user,
                null,
                accountDto.description(),
                new ArrayList<>()
        );
    }

    private BillingAddress createBillingAddressFromDto(Account account, CreateAccountDto accountDto) {
        return new BillingAddress(
                UUID.randomUUID(),
                account,
                accountDto.street(),
                accountDto.number()
        );
    }

    public List<AccountReponseDto> listAccounts(String id) {
        User user = getUserByIdOrThrow(id);

        return user.getAccounts()
                .stream()
                .map(account ->
                        new AccountReponseDto(account.getAccountId().toString(), account.getDescription()))
                .toList();
    }

    private User getUserByIdOrThrow(String id) {
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException("Usuário não pode ser encontrado ou não existe."));
    }
}
