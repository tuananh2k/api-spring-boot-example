package com.example.helloword.repositorys;

import com.example.helloword.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    int save(User user);

    int update(User user);

    int deleteById(Long id);

    int countByEmail(String email);
}
