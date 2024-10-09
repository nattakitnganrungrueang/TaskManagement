package com.taskManagement.controller;

import com.taskManagement.model.request.NewPasswordRequest;
import com.taskManagement.model.request.PasswordResetRequest;
import com.taskManagement.model.response.ApiResponse;
import com.taskManagement.model.response.AuthResponse;
import com.taskManagement.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody PasswordResetRequest request) {
        try {
            passwordResetService.createPasswordResetToken(request.getEmail());
            return ResponseEntity.ok(new ApiResponse("Password reset link sent to your email!", null));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/new-password")
    public ResponseEntity<ApiResponse> setNewPassword(@RequestBody NewPasswordRequest newPasswordRequest) {
        try {
            passwordResetService.updatePassword(newPasswordRequest.getToken(), newPasswordRequest.getNewPassword());
            return ResponseEntity.ok(new ApiResponse("Password updated successfully!", null));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
