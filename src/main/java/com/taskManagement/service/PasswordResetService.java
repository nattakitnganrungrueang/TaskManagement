package com.taskManagement.service;

import com.taskManagement.model.PasswordResetToken;
import com.taskManagement.model.User;
import com.taskManagement.repository.PasswordResetTokenRepository;
import com.taskManagement.repository.UserRepository;
import com.taskManagement.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public void createPasswordResetToken(String email) throws Exception {
        User user = userService.getUserDetailByEmail(email);
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();

        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(DateUtils.calculateExpiryDate(60));
        passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendResetPasswordEmail(user.getEmail(), token);
    }

    public void updatePassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Token has expired");
        }

        User user = resetToken.getUser();
        userService.updateUserPassword(user, newPassword);

        passwordResetTokenRepository.delete(resetToken);
    }
}
