package com.taskManagement.model.response;

import lombok.Data;

@Data
public class AuthResponse {
    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
    private String jwt;

}
