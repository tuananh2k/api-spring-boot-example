package com.example.helloword.services;

import com.example.helloword.dtos.SignUpDTO;
import com.example.helloword.models.User;
import com.example.helloword.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public int addUser(User user) {
        return userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + id + " not found."));
    }

    public int countByEmail(String email){
        return  userRepository.countByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email: " + email + " not found."));
    }

    public User editUser(SignUpDTO signUpDTO, long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID: " + userId + " not found."));

        user.setName(signUpDTO.getName());
        user.setPassword(signUpDTO.getPassword());
        user.setEmail(signUpDTO.getEmail());
        userRepository.save(user);
        return user;
    }

    public int deleteById(long id) {
       return userRepository.deleteById(id);
    }
}
