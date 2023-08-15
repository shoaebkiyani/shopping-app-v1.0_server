package com.backend.user;

import com.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public boolean isUsernameExists(String username) {
        User existingUser = userRepository.findByUsername(username);
        return existingUser != null;
    }

    public boolean wrongUsername(String username) {
        User nonExistingUser = userRepository.findByUsername(username);
        return nonExistingUser == null;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public String loginUser(User user) {
        return jwtUtils.generateToken(user);
    }
}
