package com.taskManagement.controller;

import com.taskManagement.model.request.AuthRequest;
import com.taskManagement.model.request.UserRequest;
import com.taskManagement.model.response.ApiResponse;
import com.taskManagement.model.User;
import com.taskManagement.model.response.AuthResponse;
import com.taskManagement.service.AuthenticationService;
import com.taskManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> Login(@RequestBody AuthRequest authRequest) {
        try {
            String token = authenticationService.authenticateAndGenerateToken(authRequest);
            return ResponseEntity.ok(new ApiResponse("Login successfully", new AuthResponse(token)));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(Authentication authentication){
        try {
            User user = userService.getUserDetailByEmail(authentication.getName());
            return ResponseEntity.ok(new ApiResponse("GetProfile successfully", user));
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRequest userRequest) {
        try {
            User user = userService.registerUser(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
            return new ResponseEntity<>(new ApiResponse("User registered successfully!", user.getId()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody UserRequest userRequest, Authentication authentication) {
        try {
            userService.updateUserProfile(authentication.getName(), userRequest);
            return new ResponseEntity<>(new ApiResponse("Profile updated successfully!", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("Profile update failed!", null), HttpStatus.BAD_REQUEST);
        }
    }


}
