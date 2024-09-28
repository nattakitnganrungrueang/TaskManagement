package com.taskManagement.controller;

import com.taskManagement.model.request.UserDTO;
import com.taskManagement.model.response.ApiResponse;
import com.taskManagement.model.User;
import com.taskManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    String Login() {
        return "test login ....";
    }

    @GetMapping("/profile")
    String GetProfile() {
        return "test get profile ....";
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.registerUser(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
            return new ResponseEntity<>(new ApiResponse("User registered successfully!", user.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }


}
