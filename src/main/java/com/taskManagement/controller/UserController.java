package com.taskManagement.controller;

import com.taskManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
