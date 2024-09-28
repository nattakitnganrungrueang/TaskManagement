package com.taskManagement.model.request;

import lombok.Data;

@Data
public class AuthRequest {
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    private String username;
    private String password;
}
