package com.unsa.backend.usertest;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.unsa.backend.users.Role;
import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test Service")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final String URL_BASE = "/user";

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Test case for finding a user by ID from the Controller when the post is not
     * found.
     */
    @DisplayName("Test get user by id")
    @Test
    void testGetUserById() throws Exception {
        Long userId = 1L;
        UserModel user = UserModel.builder()
                .id(userId)
                .role(Role.USER)
                .build();
        when(userService.getUser(userId)).thenReturn(user);
        mockMvc.perform(get(URL_BASE + "/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    /**
     * Test case for finding a user by ID from the Controller when the post is not
     * found.
     */
    @DisplayName("Test get user by id")
    @Test
    void testGetUserByIdNotFound() throws Exception {
        Long userId = 1L;
        when(userService.getUser(userId)).thenReturn(null);
        mockMvc.perform(get(URL_BASE + "/" + userId))
                .andExpect(status().isNotFound());
    }
}
