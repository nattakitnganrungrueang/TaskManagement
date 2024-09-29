package com.taskManagement.model.request;

import lombok.Data;

@Data
public class UserRequest {

    public UserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    private String name;
    private String email;
    private String password;
}
