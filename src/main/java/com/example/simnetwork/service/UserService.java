package com.example.simnetwork.service;

import com.example.simnetwork.model.User;
import com.example.simnetwork.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User processOAuthPostLogin(String email, String name, String picture, String googleId) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setPicture(picture);
                    newUser.setGoogleId(googleId);
                    
                    // First user becomes admin
                    if (userRepository.count() == 0) {
                        newUser.setRoles(Set.of("ROLE_ADMIN", "ROLE_USER"));
                    } else {
                        newUser.setRoles(Set.of("ROLE_USER"));
                    }
                    
                    return userRepository.save(newUser);
                });
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
} 