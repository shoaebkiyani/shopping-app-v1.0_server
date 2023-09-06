package com.backend.user;

import com.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:5173/")
@CrossOrigin(origins = "https://techzoneapi.netlify.app/")
@RequestMapping("api/v1")
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userService.allUsers();
        if (!userList.isEmpty()) {
            map.put("status", HttpStatusCode.valueOf(200));
            map.put("users", userList);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.put("status", HttpStatusCode.valueOf(404));
            map.put("message", "No user found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        Map<String, Object> map = new HashMap<>();
        if (!userService.findUserById(id)) {
            map.put("error", HttpStatusCode.valueOf(400));
            map.put("message", "Invalid details");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else {
            userService.deleteUser(id);
            map.put("status", HttpStatusCode.valueOf(200));
            map.put("message", "record deleted successfully");
            return getAllUsers();
//            return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
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
            User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),
                    User.Role.USER);
            userService.createUser(newUser);
            map.put("status", HttpStatusCode.valueOf(201));
            map.put("message", newUser.getUsername() + " with role " + newUser.getRole() + " has been " +
                    "created successfully");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        Map<String, Object> map = new HashMap<>();
        if (userService.wrongUsername(authRequest.getUsername())) {
            map.put("error", HttpStatusCode.valueOf(401));
            map.put("message", "Invalid username and/or password");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        } else {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            User user = userRepository.findByUsername(authRequest.getUsername());
            map.put("token", userService.loginUser(user));
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
}
