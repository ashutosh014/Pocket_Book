package com.pocketbook.userservice.service;

import com.pocketbook.userservice.entity.User;
import com.pocketbook.userservice.exceptions.UserEmailPresentException;
import com.pocketbook.userservice.exceptions.UserNamePresentException;
import com.pocketbook.userservice.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    public User createUser(User user) throws UserNamePresentException, UserEmailPresentException;

    public List<User> getAllUsers();

    public User getUserById(Long id) throws UserNotFoundException;

    public User updateUser(Long id,User user) throws UserNotFoundException, UserNamePresentException, UserEmailPresentException;

    public void deleteUser(Long id) throws UserNotFoundException;
}
