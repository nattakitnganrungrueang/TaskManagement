package com.taskManagement.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnauthorizedAccessToUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAuthorizedAccessToUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile"))
                .andExpect(status().isOk());
    }

}
