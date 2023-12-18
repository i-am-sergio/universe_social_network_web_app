package com.unsa.backend.usertest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

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
    private static final String URL_BASE_ID = "/user/{id}";
    private static final String URL_BASE_ID_FOLLOW = "/user/{id}/follow";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String URL_BASE_ID_UNFOLLOW = "/user/{id}/unfollow";
    private static final String JSON_BODY = "{ \"firstname\": \"name updated\", \"lastname\": \"lastname updated\", \"role\": \"USER\" }";

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Test case for finding all users from the Controller.
     */
    @DisplayName("Test get all users")
    @Test
    void testGetAllUsers() throws Exception {
        UserModel user = UserModel.builder()
                .id(1L)
                .role(Role.USER)
                .build();
        when(userService.getUsers()).thenReturn(List.of(user));
        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    /**
     * Test case for finding all users from the Controller and give error
     */
    @DisplayName("Test get all users")
    @Test
    void testGetAllUsersError() throws Exception {
        when(userService.getUsers()).thenThrow(new RuntimeException());
        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isInternalServerError());
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

    /**
     * Test case for finding a user by ID from the Controller and give error 500
     */
    @DisplayName("Test get user by id")
    @Test
    void testGetUserByIdError() throws Exception {
        Long userId = 1L;
        when(userService.getUser(userId)).thenThrow(new RuntimeException());
        mockMvc.perform(get(URL_BASE + "/" + userId))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test case for updating a user by ID from the Controller.
     */
    @Test
    @DisplayName("Test update user")
    void updateUserSuccess() throws Exception {
        Long userId = 1L;
        UserModel userUpdated = new UserModel();
        userUpdated.setId(userId);
        userUpdated.setFirstname("name updated");
        userUpdated.setLastname("lastname updated");
        userUpdated.setRole(Role.USER);
        when(userService.updateUser(eq(userId), any(UserModel.class))).thenReturn(userUpdated);
        mockMvc.perform(put(URL_BASE_ID, userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSON_BODY))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.firstname", is("name updated")))
            .andExpect(jsonPath("$.lastname", is("lastname updated")));
    }

    /**
     * Test case for updating a user by ID from the Controller when the post is not
     * found.
     */
    @Test
    @DisplayName("Test update user")
    void updateUserNotFound() throws Exception {
        Long userId = 1L;
        when(userService.updateUser(eq(userId), any(UserModel.class))).thenReturn(null);
        mockMvc.perform(put(URL_BASE_ID, userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSON_BODY))
            .andExpect(status().isNotFound());
    }
    
    /**
     * Test case for updating a user by ID from the Controller and give error 500
     */
    @DisplayName("Test update user")
    @Test
    void testUpdateUserError() throws Exception {
        Long userId = 1L;
        when(userService.updateUser(eq(userId), any(UserModel.class))).thenThrow(new RuntimeException());
        mockMvc.perform(put(URL_BASE_ID, userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSON_BODY))
            .andExpect(status().isInternalServerError());
    }
    
    /*
     * Deleteuser test:
     * Verify if a user is deleted correctly.
     * Try if a 204 code (without content) is returned after elimination.
     * Check if a code 404 is obtained if the user cannot be found.
     */
    @Test
    @DisplayName("Test delete user")
    void deleteUserSuccess() throws Exception {
        UserModel userToDelete = new UserModel();
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(userToDelete);
        mockMvc.perform(delete(URL_BASE_ID, userId))
                .andExpect(status().isNoContent());
        verify(userService).deleteUser(userId);
    }

    /*
     * Test case for deleting a user by ID from the Controller when the user is not
     * found.
     */
    @Test
    @DisplayName("Test delete user")
    void deleteUserNotFound() throws Exception {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(null);
        mockMvc.perform(delete(URL_BASE + "/" + userId))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case for deleting a user by ID from the Controller and give error 500
     */
    @DisplayName("Test delete user")
    @Test
    void testDeleteUserError() throws Exception {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenThrow(new RuntimeException());
        mockMvc.perform(delete(URL_BASE + "/" + userId))
                .andExpect(status().isInternalServerError());
    }

    /*
     * Test case for following a user by ID from the Controller.
     */

    @Test
    @DisplayName("Test follow user")
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
        mockMvc.perform(put(URL_BASE_ID_FOLLOW, targetUserId)
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
    @DisplayName("Test follow user")
    void followUserNotFound() throws Exception {
        Long targetUserId = 2L;
        Authentication authentication = mock(Authentication.class);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new EntityNotFoundException(USER_NOT_FOUND))
                .when(userService).followUser(anyLong(), anyLong());
        mockMvc.perform(put(URL_BASE_ID_FOLLOW, targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(USER_NOT_FOUND));
    }

    /*
     * Test case for following a user by ID from the Controller and give error 500
     */
    @DisplayName("Test follow user")
    @Test
    void testFollowUserError() throws Exception {
        Long targetUserId = 2L;
        Authentication authentication = mock(Authentication.class);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new RuntimeException())
                .when(userService).followUser(anyLong(), anyLong());
        mockMvc.perform(put(URL_BASE_ID_FOLLOW, targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }

    /*
     * Test for unfotolowuser:
     * Verify if a user can stop following another user.
     * Try if a successful message is returned (code 200) after stopping.
     * Check if an error is obtained (Code 404) when the user to stop following is
     * not found.
     */

    @Test
    @DisplayName("Test unfollow user")
    void unfollowUserSuccess() throws Exception {
        Long followerId = 1L;
        Long targetUserId = 2L;
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(followerId);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doNothing().when(userService).unfollowUser(anyLong(), anyLong());
        mockMvc.perform(put(URL_BASE_ID_UNFOLLOW, targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User unfollowed!"));
    }

    /*
     * Test case for unfollowing a user by ID from the Controller when the user is
     * not found.
     */
    @Test
    @DisplayName("Test unfollow user")
    void unfollowUserNotFound() throws Exception {
        Long followerId = 1L;
        Long targetUserId = 2L;
        Authentication authentication = mock(Authentication.class);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(followerId);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new EntityNotFoundException(USER_NOT_FOUND))
                .when(userService).unfollowUser(anyLong(), anyLong());
        mockMvc.perform(put(URL_BASE_ID_UNFOLLOW, targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(USER_NOT_FOUND));
    }

    /*
     * Test case for unfollowing a user by ID from the Controller and give error 500
     */
    @DisplayName("Test unfollow user")
    @Test
    void testUnfollowUserError() throws Exception {
        Long followerId = 1L;
        Long targetUserId = 2L;
        Authentication authentication = mock(Authentication.class);
        UserModel userModel = mock(UserModel.class);
        when(userModel.getId()).thenReturn(followerId);
        when(authentication.getPrincipal()).thenReturn(userModel);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doThrow(new RuntimeException())
                .when(userService).unfollowUser(anyLong(), anyLong());
        mockMvc.perform(put(URL_BASE_ID_UNFOLLOW, targetUserId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
}
