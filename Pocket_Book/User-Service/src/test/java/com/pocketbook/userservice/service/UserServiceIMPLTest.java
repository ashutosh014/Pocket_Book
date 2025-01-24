package com.pocketbook.userservice.service;

import com.pocketbook.userservice.entity.User;
import com.pocketbook.userservice.exceptions.UserEmailPresentException;
import com.pocketbook.userservice.exceptions.UserNamePresentException;
import com.pocketbook.userservice.exceptions.UserNotFoundException;
import com.pocketbook.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceIMPLTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceIMPL userService;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .userId(1L)
                .userName("testUser")
                .userEmail("test.user@test.com")
                .build();
    }


    @Test
    void createUser_whenUserNameExists_throwsUserNamePresentException() {

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Assertions.assertThrows(UserNamePresentException.class, () -> userService.createUser(user));
        Mockito.verify(userRepository, Mockito.never()).save(user);

    }

    @Test
    void createUser_whenUserEmailExists_throwsUserEmailPresentException() {

        User newUser = User.builder()
                    .userId(2L)
                    .userName("newUser")
                    .userEmail("test.user@test.com")
                    .build();

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Assertions.assertThrows(UserEmailPresentException.class, () -> userService.createUser(newUser));
        Mockito.verify(userRepository, Mockito.never()).save(newUser);
    }

    @Test
    void createUser_savesUserSuccessfully() throws UserEmailPresentException, UserNamePresentException {

        Mockito.when(userRepository.findAll()).thenReturn(List.of());
        userService.createUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    void getAllUsers_returnsListOfUsers() {

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.getAllUsers();

        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(1, users.size());
        Mockito.verify(userRepository,Mockito.times(1)).findAll();

    }

    @Test
    void getUserById_whenUserExists_returnsUser() throws UserNotFoundException {

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getUserName(), foundUser.getUserName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getUserById_whenUserDoesNotExist_throwsUserNotFoundException() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateUser_whenUserExists_updatesUser() throws UserNotFoundException, UserEmailPresentException, UserNamePresentException {

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findAll()).thenReturn(List.of());
        user.setUserName("updateUser");
        userService.updateUser(1L, user);
        Mockito.verify(userRepository,Mockito.times(1)).save(user);

    }

    @Test
    void updateUser_whenUserDoesNotExists_throwsUserNotFoundException() {

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L,user));
    }

    @Test
    void updateUser_whenUserExistsAndNameAlreadyPresent_throwsUserNamePresentException()  {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        user.setUserName("newUser");
        Assertions.assertThrows(UserNamePresentException.class, () -> userService.updateUser(1L,user));
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void updateUser_whenUserExistsAndEmailAlreadyPresent_throwsUserEmailPresentException() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        User updatedUser = User.builder()
                .userId(1L)
                .userName("user")
                .userEmail("test.user@test.com")
                .build();
        Assertions.assertThrows(UserEmailPresentException.class, () -> userService.updateUser(1L, updatedUser));
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    void deleteUser_whenUserExists_deletesUser() throws UserNotFoundException {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }

    @Test
    void deleteUser_whenUserDoesNotExist_throwsUserNotFoundException() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
        Mockito.verify(userRepository,Mockito.never()).delete(user);
    }
}
