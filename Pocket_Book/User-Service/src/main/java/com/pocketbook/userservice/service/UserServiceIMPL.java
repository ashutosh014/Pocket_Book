package com.pocketbook.userservice.service;

import com.pocketbook.userservice.entity.User;
import com.pocketbook.userservice.exceptions.UserEmailPresentException;
import com.pocketbook.userservice.exceptions.UserNamePresentException;
import com.pocketbook.userservice.exceptions.UserNotFoundException;
import com.pocketbook.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceIMPL implements UserService{

    private static final Logger Logger = LoggerFactory.getLogger(UserServiceIMPL.class);

    private final UserRepository userRepository;

    public UserServiceIMPL(UserRepository userRepository) {
        this.userRepository = userRepository;
        Logger.info("Inside the User Service IMPL class");
    }

    @Override
    public User createUser(User user) throws UserNamePresentException, UserEmailPresentException {
        // Check if the username and user email is not present before in the database
        if(!isUserNameUnique(user.getUserName())) {
            Logger.error("Username {} is already present ", user.getUserName());
            throw new UserNamePresentException("User Name already exists. Kindly enter a different user name.");
        }

        if(!isUserEmailUnique(user.getUserEmail())) {
            Logger.error("Email address : {} is already present", user.getUserEmail());
            throw new UserEmailPresentException("Email address is already in use. Kindly use a different email address");
        }

        Logger.info("Saving the user in the database");
        return userRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        Logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        Logger.info("Fetching user details by user id : {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("No user found with id : " + id));
    }

    @Override
    public User updateUser(Long id,User user) throws UserNotFoundException, UserNamePresentException, UserEmailPresentException {
        Logger.info("Fetching user details by user id : {}", id);
        Optional<User> userDetails = userRepository.findById(id);

        if(!isUserNameUnique(user.getUserName())) {
            throw new UserNamePresentException("User Name already exists. Kindly enter a different user name.");
        }

        if(!isUserEmailUnique(user.getUserEmail())) {
            throw new UserEmailPresentException("Email address is already in use. Kindly use a different email address");
        }

        Logger.info("Updating the user details");
        if(userDetails.isPresent()) {
            User updatedUser = User.builder()
                    .userId(id)
                    .userName(userDetails.get().getUserName())
                    .userEmail(userDetails.get().getUserEmail())
                    .build();


            return userRepository.save(updatedUser);
        }
        throw new UserNotFoundException("No user found with id : " + id);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        Optional<User> userDetails = userRepository.findById(id);

        Logger.info("Deleting the user");
        if(userDetails.isPresent()) {
            userRepository.delete(userDetails.get());
        }
        else {
            throw new UserNotFoundException("No user found with id : " + id);
        }

    }


    private boolean isUserEmailUnique(String userEmail) {
        List<User> users = userRepository.findAll();
        long count = users.stream()
                .filter(user -> user.getUserEmail().equals(userEmail))
                .count();

        return count == 0;
    }

    private boolean isUserNameUnique(String userName) {
        List<User> users = userRepository.findAll();
        long count = users.stream()
                .filter(user -> user.getUserName().equals(userName))
                .count();

        return count == 0;
    }
}
