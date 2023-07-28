package com.springsecurity.springsecurity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userService.allUsers();
        if(!userList.isEmpty()) {
            map.put("status", HttpStatusCode.valueOf(200));
            map.put("users", userList);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.put("status", HttpStatusCode.valueOf(404));
            map.put("message", "No user found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
            if (userService.isUsernameExists(user.getUsername())) {
                map.put("error", HttpStatusCode.valueOf(403));
                map.put("message", "User already exist");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            } else {
                User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
                userService.createUser(newUser);
                map.put("status", HttpStatusCode.valueOf(201));
                map.put("message", newUser.getUsername() + " created successfully");
                return new ResponseEntity<>(map, HttpStatus.CREATED);
            }
    }
}
