package com.taskManagement.service;

import com.taskManagement.model.User;
import com.taskManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String name, String email, String rawPassword) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already exists!");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(name, email, encodedPassword);
        return userRepository.save(user);
    }


}
