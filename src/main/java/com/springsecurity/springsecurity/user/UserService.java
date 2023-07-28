package com.springsecurity.springsecurity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean isUsernameExists(String username) {
        User existingUser = userRepository.findByUsername(username);
        return existingUser != null;
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user){
        userRepository.save(user);
    }
}
