package com.unsa.backend.usertest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.anyLong;

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

import jakarta.persistence.EntityNotFoundException;

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
     * Test case for finding a user by ID from the Controller.
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

    /*
     * Deleteuser test:
     * Verify if a user is deleted correctly.
     * Try if a 204 code (without content) is returned after elimination.
     * Check if a code 404 is obtained if the user cannot be found.
     */

    @Test
    @DisplayName("Delete User - Success")
    void deleteUserSuccess() throws Exception {
        UserModel userToDelete = new UserModel();
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(userToDelete);
        mockMvc.perform(delete(URL_BASE + "/{id}", userId))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(userId);
    }

    /*
     * Test case for deleting a user by ID from the Controller when the user is not
     * found.
     */
    @Test
    @DisplayName("Delete User - Not Found")
    void deleteUserNotFound() throws Exception {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(null);
        mockMvc.perform(delete(URL_BASE + "/" + userId))
                .andExpect(status().isNotFound());
    }

    /*
     * Test case for following a user by ID from the Controller.
     */

    @Test
    @DisplayName("Follow User - Success")
    void followUserSuccess() throws Exception {
        Long targetUserId = 2L;
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(1L);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doNothing().when(userService).followUser(anyLong(), anyLong());

        mockMvc.perform(put(URL_BASE + "/{id}/follow", targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User followed!"));
    }

    /*
     * Test case for following a user by ID from the Controller when the user is not
     * found.
     */
    @Test
    @DisplayName("Follow User - Not Found")
    void followUserNotFound() throws Exception {
        Long targetUserId = 2L;
        Authentication authentication = mock(Authentication.class);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new EntityNotFoundException("User not found"))
                .when(userService).followUser(anyLong(), anyLong());
        mockMvc.perform(put(URL_BASE + "/{id}/follow", targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
    /*
     * Test for unfotolowuser:
     * Verify if a user can stop following another user.
     * Try if a successful message is returned (code 200) after stopping.
     * Check if an error is obtained (Code 404) when the user to stop following is
     * not found.
     */

    @Test
    @DisplayName("Unfollow User - Success")
    void unfollowUserSuccess() throws Exception {
        Long followerId = 1L;
        Long targetUserId = 2L;

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(new UserModel());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        doNothing().when(userService).unfollowUser(followerId, targetUserId);

        mockMvc.perform(put(URL_BASE + "/{id}/unfollow", targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User unfollowed!"));

    }

}
