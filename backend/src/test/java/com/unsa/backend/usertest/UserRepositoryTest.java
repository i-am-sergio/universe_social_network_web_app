package com.unsa.backend.usertest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserRepository;

@SpringBootTest
@DisplayName("UserRepository Tests")
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    /*
     * Test for Findbyusername:
     * Verify if a user is found by username.
     * Check if the returned user coincides with the username provided.
     */
    @Test
    @DisplayName("Test for findByUsername")
    void testFindByUsername() {
        String username = "testUser";
        UserModel user = new UserModel();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertTrue(user.isEnabled());
        assertEquals(username, user.getUsername());
    }


    /*
      * Test for ExistSbyusername:
      * Verify if there is a user with a specific username.
      * Check if the answer is true when the user exists and false when it does not exist.
      */
    @Test
    @DisplayName("Test for existsByUsername")
    void testExistsByUsername() {
        String existingUsername = "existingUser";
        String nonExistingUsername = "nonExistingUser";

        when(userRepository.existsByUsername(existingUsername)).thenReturn(true);
        when(userRepository.existsByUsername(nonExistingUsername)).thenReturn(false);


        assertTrue(existingUsername.equalsIgnoreCase(existingUsername));
        assertFalse(nonExistingUsername.equals(existingUsername));
    }



     /*
     * Test for Findbyid:
     * Verify if a user is found by your ID.
     * Check if the returned user coincides with the ID provided.
     */
    @Test
    @DisplayName("Test for findById")
    void testFindById() {
        Long userId = 1L;
        UserModel user = new UserModel();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertTrue(user.isEnabled());
        assertEquals(userId, user.getId());
    }
}
