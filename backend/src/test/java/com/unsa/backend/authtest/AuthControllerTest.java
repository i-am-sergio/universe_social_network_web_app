package com.unsa.backend.authtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.unsa.backend.auth.AuthService;
import com.unsa.backend.auth.LoginRequest;
import com.unsa.backend.auth.RegisterRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test Auth")
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private static final String URL_BASE = "/auth";
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String JSON_CONTENT = "{\"username\": \"user1@gmail.com\", \"password\": \"admin\"}";
    private static final String JSON_REGISTER_CONTENT = "{\"username\": \"user200@gmail.com\", \"password\": \"admin200\", \"firstname\": \"John\", \"lastname\": \"Doe\", \"country\": \"Yugoslavia\"}";
    private static final String INTERNAL_ERROR = "Internal Server Error";

    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
            this.mockMvc = mockMvc;
    }

    @DisplayName("Test valid login")
    @Test
    void testValidLogin() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().username("user1@gmail.com").password("admin").build();
        when(authService.login(loginRequest)).thenReturn(null);
        mockMvc.perform(post(URL_BASE + "/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(JSON_CONTENT))
                .andExpect(status().isOk());
    }

    @DisplayName("Test invalid login")
    @Test
    void testInvalidLogin() throws Exception {
        doThrow(new RuntimeException(INTERNAL_ERROR)).when(authService).login(any(LoginRequest.class));
        mockMvc.perform(post(URL_BASE + "/login")
                .contentType(JSON_CONTENT_TYPE)
                .content(JSON_CONTENT))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Test Valid Register")
    @Test
    void testValidRegister() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder().username("user200@gmail.com")
        .password("admin200").firstname("John").lastname("Doe").country("Yugoslavia").build();
        when(authService.register(registerRequest)).thenReturn(null);
        mockMvc.perform(post(URL_BASE + "/register")
            .contentType(JSON_CONTENT_TYPE)
            .content(JSON_REGISTER_CONTENT))
            .andExpect(status().isOk());
    }

    @DisplayName("Test Invalid Register")
    @Test
    void testInvalidRegister() throws Exception {
        doThrow(new RuntimeException(INTERNAL_ERROR)).when(authService).register(any(RegisterRequest.class));
        mockMvc.perform(post(URL_BASE + "/register")
            .contentType(JSON_CONTENT_TYPE)
            .content(JSON_REGISTER_CONTENT))
            .andExpect(status().isInternalServerError());
    }

    @DisplayName("Test Incorrect Login")
    @Test
    void testIncorrectLogin() throws Exception {
        mockMvc.perform(post(URL_BASE + "/login")
                .contentType(JSON_CONTENT_TYPE)
                .content("null"))
                .andExpect(status().isBadRequest());
    }


}   