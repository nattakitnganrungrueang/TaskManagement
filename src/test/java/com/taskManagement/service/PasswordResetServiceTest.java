package com.taskManagement.service;

import com.taskManagement.model.PasswordResetToken;
import com.taskManagement.model.User;
import com.taskManagement.repository.PasswordResetTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PasswordResetServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPasswordResetToken_userExists_sendsEmail() throws Exception {
        String email = "user@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        when(userService.getUserDetailByEmail(email)).thenReturn(mockUser);
        passwordResetService.createPasswordResetToken(email);
        verify(emailService).sendResetPasswordEmail(eq(email), anyString());
    }

    @Test
    void updatePassword_validToken_updatesPassword() {
        String token = "validToken";
        String newPassword = "newPassword";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        PasswordResetToken mockToken = new PasswordResetToken();
        User mockUser = new User();
        mockToken.setUser(mockUser);
        mockToken.setExpiryDate(calendar.getTime());
        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(mockToken));
        passwordResetService.updatePassword(token, newPassword);
        verify(userService).updateUserPassword(mockUser, newPassword);
        verify(passwordResetTokenRepository).delete(mockToken);
    }
}
