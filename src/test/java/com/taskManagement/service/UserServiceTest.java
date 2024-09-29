package com.taskManagement.service;

import com.taskManagement.model.User;
import com.taskManagement.model.request.UserRequest;
import com.taskManagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        User user = new User("John Doe", "john.doe@example.com", encodedPassword);

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser("John Doe", "john.doe@example.com", rawPassword);
        assertNotNull(result);
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    public void testRegisterUser_DuplicateEmail() {
        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.registerUser("John Doe", "john.doe@example.com", "password123");
        });
        assertEquals("Email already exists!", exception.getMessage());
    }

    @Test
    public void testFindByEmail_Success() throws Exception {
        String email = "john.doe@example.com";
        User mockUser = new User("John Doe", email);
        when(userRepository.findByEmail(email)).thenReturn(mockUser);
        User result = userService.getUserDetailByEmail(email);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        String email = "john.doe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.getUserDetailByEmail(email);
        });
        assertEquals("Email is not exists!", exception.getMessage());
    }

    @Test
    public void testUpdateUserProfile_Success() throws Exception {
        String email = "john.doe@example.com";
        User mockUser = new User("John Doe", email);
        UserRequest updateUserRequest = new UserRequest("Jane Doe", "jane.doe@example.com");
        when(userRepository.findByEmail(email)).thenReturn(mockUser);
        userService.updateUserProfile(email, updateUserRequest);
        assertEquals("Jane Doe", mockUser.getName());
        assertEquals("jane.doe@example.com", mockUser.getEmail());
        verify(userRepository, times(1)).save(mockUser);
    }

}
