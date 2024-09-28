package com.taskManagement.service;

import com.taskManagement.model.User;
import com.taskManagement.model.request.AuthRequest;
import com.taskManagement.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    public String authenticateAndGenerateToken(AuthRequest authRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetail = userService.getUserDetailByEmail(authRequest.getUsername());
        return jwtUtils.generateToken(userDetail);
    }

}
