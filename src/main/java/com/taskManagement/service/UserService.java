package com.taskManagement.service;

import com.taskManagement.model.User;
import com.taskManagement.model.request.UserRequest;
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

    public User getUserDetailByEmail(String email) throws Exception{
        User userDetail = userRepository.findByEmail(email);
        if (userDetail == null) {
            throw new Exception("Email is not exists!");
        }
        return userDetail;
    }

    public void updateUserProfile(String email, UserRequest userDTO) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    public void updateUserPassword(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


}
