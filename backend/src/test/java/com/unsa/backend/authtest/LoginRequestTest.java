package com.unsa.backend.authtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.auth.LoginRequest;

@SpringBootTest
@DisplayName("Test Login Request")
class LoginRequestTest {

    private static String testUsername = "testUsername";
    private static String testPassword = "testPassword";
    
    @Test
    void testConstructorWithArguments() {
        LoginRequest loginRequest = new LoginRequest(testUsername, testPassword);
        assertEquals(testUsername, loginRequest.getUsername());
        assertEquals(testPassword, loginRequest.getPassword());
    }

    @Test
    void testConstructorWithoutArguments() {
        LoginRequest loginRequest = new LoginRequest();
        assertNull(loginRequest.getUsername());
        assertNull(loginRequest.getPassword());
    }

    @Test
    void testBuilder() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username(testUsername)
                .password(testPassword)
                .build();

        assertEquals(testUsername, loginRequest.getUsername());
        assertEquals(testPassword, loginRequest.getPassword());
    }

    @Test
    void testGetterSetter() {
        // given
        LoginRequest loginRequest = new LoginRequest();

        // when
        loginRequest.setUsername(testUsername);
        loginRequest.setPassword(testPassword);

        // then
        assertEquals(testUsername, loginRequest.getUsername());
        assertEquals(testPassword, loginRequest.getPassword());
    }

    
}