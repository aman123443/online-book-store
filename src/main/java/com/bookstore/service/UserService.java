// filepath: c:\Users\ASUS\Desktop\online-book\online-book-store\src\main\java\com\bookstore\service\UserService.java
package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

  
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}