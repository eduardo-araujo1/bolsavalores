package com.eduardo.agregadorinvestimentos.service;

import com.eduardo.agregadorinvestimentos.dto.AccountReponseDto;
import com.eduardo.agregadorinvestimentos.dto.CreateAccountDto;
import com.eduardo.agregadorinvestimentos.dto.UserDto;
import com.eduardo.agregadorinvestimentos.entity.Account;
import com.eduardo.agregadorinvestimentos.entity.BillingAddress;
import com.eduardo.agregadorinvestimentos.entity.User;
import com.eduardo.agregadorinvestimentos.repository.AccountRepository;
import com.eduardo.agregadorinvestimentos.repository.BillingAdressRepository;
import com.eduardo.agregadorinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BillingAdressRepository billingAdressRepository;

    @InjectMocks
    private UserService service;

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto("username", "email@example.com", "password");
        User userEntity = new User(UUID.randomUUID(), userDto.username(), userDto.email(), userDto.password(), Instant.now(), Instant.now());

        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        User createdUser = service.createUser(userDto);

        assertNotNull(createdUser);
        assertEquals(userEntity.getUserId(), createdUser.getUserId());
        assertEquals(userDto.username(), createdUser.getUsername());
        assertEquals(userDto.email(), createdUser.getEmail());
        assertEquals(userDto.password(), createdUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById_UserExists() {
        String userId = UUID.randomUUID().toString();
        User existingUser = new User(UUID.fromString(userId), "username", "email@example.com", "password", Instant.now(), Instant.now());
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.of(existingUser));

        Optional<User> foundUser = service.getUserById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(existingUser, foundUser.get());
    }

    @Test
    public void testGetUserById_UserNotExists() {
            String userId = UUID.randomUUID().toString();
            when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.getUserById(userId))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Usuário não pode ser encontrado ou não existe.");
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(UUID.randomUUID(), "user1", "user1@example.com", "password1", Instant.now(), Instant.now()));
        users.add(new User(UUID.randomUUID(), "user2", "user2@example.com", "password2", Instant.now(), Instant.now()));
        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = service.findAllUsers();

        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(users.size());
        assertThat(foundUsers).containsExactlyElementsOf(users);
    }

    @Test
    public void testDeleteUser_UserExists() {
        String userId = UUID.randomUUID().toString();
        User existingUser = new User(UUID.fromString(userId), "username", "email@example.com", "password", Instant.now(), Instant.now());
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.of(existingUser));

        service.deleteUser(userId);

        verify(userRepository, times(1)).findById(UUID.fromString(userId));
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    public void  testDeleteUser_UserNotExists() {
        String userId = UUID.randomUUID().toString();
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteUser(userId))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testUpdateUser() {
        String userId = UUID.randomUUID().toString();
        UserDto userDto = new UserDto("newUsername", "newEmail@example.com", "newPassword");
        User existingUser = new User(UUID.fromString(userId), "username", "email@example.com", "password", Instant.now(), Instant.now());
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = service.update(userId, userDto);

        verify(userRepository, times(1)).findById(UUID.fromString(userId));
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo(userDto.username());
        assertThat(updatedUser.getEmail()).isEqualTo(userDto.email());
        assertThat(updatedUser.getPassword()).isEqualTo(userDto.password());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void  testUptdate_UserNotExists() {
        String userId = UUID.randomUUID().toString();
        UserDto userDto = new UserDto("newUsername", "newEmail@example.com", "newPassword");
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(userId, userDto))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void testCreateAccount() {
        String userId = UUID.randomUUID().toString();
        CreateAccountDto accountDto = new CreateAccountDto("Description", "Street", 123);
        User user = new User(UUID.fromString(userId), "username", "email@example.com", "password", Instant.now(), Instant.now());
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.of(user));

        service.createAccount(userId, accountDto);

        verify(userRepository, times(1)).findById(UUID.fromString(userId));
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(billingAdressRepository, times(1)).save(any(BillingAddress.class));
    }

    @Test
    public void testCreateAccount_UserNotFound() {
        String userId = UUID.randomUUID().toString();
        CreateAccountDto accountDto = new CreateAccountDto("Description", "Street", 123);
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.empty());


        assertThatThrownBy(() -> service.createAccount(userId, accountDto))
                .isInstanceOf(RuntimeException.class);

        verify(userRepository, times(1)).findById(UUID.fromString(userId));
        verifyNoInteractions(accountRepository);
        verifyNoInteractions(billingAdressRepository);
    }

    @Test
    public void testListAccounts() {
        String userId = UUID.randomUUID().toString();
        User user = new User(UUID.fromString(userId), "username", "email@example.com", "password", Instant.now(), Instant.now());
        Account account1 = new Account(UUID.randomUUID(), user, null, "Description 1", new ArrayList<>());
        Account account2 = new Account(UUID.randomUUID(), user, null, "Description 2", new ArrayList<>());
        user.setAccounts(List.of(account1, account2));
        when(userRepository.findById(UUID.fromString(userId))).thenReturn(Optional.of(user));

        List<AccountReponseDto> accountResponseDtos = service.listAccounts(userId);

        assertThat(accountResponseDtos).hasSize(2);
        assertThat(accountResponseDtos.get(0).id()).isEqualTo(account1.getAccountId().toString());
        assertThat(accountResponseDtos.get(0).description()).isEqualTo(account1.getDescription());
        assertThat(accountResponseDtos.get(1).id()).isEqualTo(account2.getAccountId().toString());
        assertThat(accountResponseDtos.get(1).description()).isEqualTo(account2.getDescription());
        verify(userRepository, times(1)).findById(UUID.fromString(userId));
    }

}
