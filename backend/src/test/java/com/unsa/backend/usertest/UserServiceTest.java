package com.unsa.backend.usertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserRepository;
import com.unsa.backend.users.UserService;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Test Service")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;



     /*
     * Test for getusers:
     * Verify if all users are obtained.
     * Check if the user list is not empty.
     */
    @Test
    @DisplayName("Test for getUsers")
    void testGetUsers() {
        UserModel user1 = new UserModel();
        UserModel user2 = new UserModel();
        List<UserModel> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<UserModel> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }



    /*
      * Test for Getuser:
      * Verify if a user is obtained by your ID.
      * Check if the user password is deleted correctly.
      */
    @Test
    @DisplayName("Test for getUser")
    void testGetUser() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setPassword("password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserModel result = userService.getUser(userId);

        assertNotNull(result);
        assertNull(result.getPassword());
    }



    /*
     * Test for Updateuser:
     * Verify if a user is updated correctly.
     * Check if the updated user password is deleted correctly.
     */
    @Test
    @DisplayName("Test for updateUser")
    void testUpdateUser() {
        Long userId = 1L;
        UserModel originalUser = new UserModel();
        originalUser.setFirstname("Nel");
        originalUser.setLastname("zon");
        originalUser.setPassword("password");

        UserModel updatedUser = new UserModel();
        updatedUser.setFirstname("Updated Nel");
        updatedUser.setLastname("Updated zon");

        when(userRepository.findById(userId)).thenReturn(Optional.of(originalUser));
        when(userRepository.save(originalUser)).thenReturn(originalUser);

        UserModel result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("Updated Nel", result.getFirstname());
        assertEquals("Updated zon", result.getLastname());
        assertNull(result.getPassword());
    }



    /*
     * Deleteuser test:
     * Verify if a user is deleted correctly.
     * Check if the user's password eliminated is properly removed.
     */
    @Test
    @DisplayName("Test for deleteUser")
    void testDeleteUser() {
        Long userId = 1L;
        UserModel userToDelete = new UserModel();
        userToDelete.setFirstname("Nel");
        userToDelete.setLastname("zon");
        userToDelete.setPassword("password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userToDelete));

        UserModel deletedUser = userService.deleteUser(userId);

        assertNotNull(deletedUser);
        assertEquals("Nel", deletedUser.getFirstname());
        assertEquals("zon", deletedUser.getLastname());
        assertNull(deletedUser.getPassword());
        verify(userRepository, times(1)).delete(userToDelete);
    }



     /*
     * Followuser test:
     * Verify if a user can follow another user.
     * Check if the follower and the user is correctly added to the respective lists.
     */
    @Test
    @DisplayName("Test for followUser")
    void testFollowUser() {
        Long followerId = 1L;
        Long targetUserId = 2L;

        UserModel follower = new UserModel();
        follower.setId(followerId);
        follower.setFollowing(new ArrayList<>());

        UserModel targetUser = new UserModel();
        targetUser.setId(targetUserId);
        targetUser.setFollowers(new ArrayList<>());

        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));

        userService.followUser(followerId, targetUserId);

        assertTrue(targetUser.getFollowers().contains(followerId));
        assertTrue(follower.getFollowing().contains(targetUserId));
        verify(userRepository, times(1)).save(follower);
        verify(userRepository, times(1)).save(targetUser);
    }



    /*
     * Test for unfotolowuser:
     * Verify if a user can stop following another user.
     * Check if the follower and the user are correctly eliminated followed by the respective lists.
     */
    @Test
    @DisplayName("Test for unfollowUser")
    void testUnfollowUser() {
        Long followerId = 1L;
        Long targetUserId = 2L;

        UserModel follower = new UserModel();
        follower.setId(followerId);
        follower.setFollowing(new ArrayList<>(List.of(targetUserId)));

        UserModel targetUser = new UserModel();
        targetUser.setId(targetUserId);
        targetUser.setFollowers(new ArrayList<>(List.of(followerId)));

        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userRepository.findById(targetUserId)).thenReturn(Optional.of(targetUser));

        userService.unfollowUser(followerId, targetUserId);

        assertFalse(targetUser.getFollowers().contains(followerId));
        assertFalse(follower.getFollowing().contains(targetUserId));
        verify(userRepository, times(1)).save(follower);
        verify(userRepository, times(1)).save(targetUser);
    }
}
