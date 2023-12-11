package com.example.helloword.controllers;

import com.example.helloword.dtos.LoginDTO;
import com.example.helloword.dtos.SignUpDTO;
import com.example.helloword.models.User;
import com.example.helloword.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.findByEmail(loginDTO.getEmail());
        boolean isValid = Objects.equals(user.getPassword(), loginDTO.getPassword());
        if (isValid) {
            return ResponseEntity.ok(user);
        }
        Map<String, String> data = new HashMap<>();
        data.put("status", "error");
        data.put("message", "Email or password is incorrect");
        return new ResponseEntity<>(data, HttpStatusCode.valueOf(400));
    }

    @PostMapping("/add")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO) {
        int count = userService.countByEmail(signUpDTO.getEmail());
        if (count > 0) {
            Map<String, String> data = new HashMap<>();
            data.put("status", "error");
            data.put("message", "Email is exits");
            return new ResponseEntity<>(data, HttpStatusCode.valueOf(400));
        }
        User newUser = new User();
        newUser.setName(signUpDTO.getName());
        newUser.setEmail(signUpDTO.getEmail());
        newUser.setPassword(signUpDTO.getPassword());
        int isSuccess = userService.addUser(newUser);
        if (isSuccess == 1) {
            return new ResponseEntity<>(newUser, HttpStatusCode.valueOf(200));
        }
        Map<String, String> err = new HashMap<>();
        err.put("status", "error");
        err.put("message", "Add user is failed");
        return new ResponseEntity<>(err, HttpStatusCode.valueOf(400));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@RequestBody SignUpDTO user, @PathVariable("id") Long userId) {
        User newUser = userService.editUser(user, userId);
        return new ResponseEntity<>(newUser, HttpStatusCode.valueOf(200));
    }
}
