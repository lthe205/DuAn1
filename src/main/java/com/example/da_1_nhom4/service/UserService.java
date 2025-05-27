package com.example.da_1_nhom4.service;

import com.example.da_1_nhom4.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}