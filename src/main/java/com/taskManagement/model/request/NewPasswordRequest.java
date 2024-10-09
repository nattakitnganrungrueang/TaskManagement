package com.taskManagement.model.request;

import lombok.Data;

@Data
public class NewPasswordRequest {
    private String token;
    private String newPassword;
}
