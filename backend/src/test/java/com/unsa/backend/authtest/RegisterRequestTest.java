package com.unsa.backend.authtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.auth.RegisterRequest;

@SpringBootTest
@DisplayName("Test Register Request")
class RegisterRequestTest {

    private static String testUsername = "testUsername";
    private static String testPassword = "testPassword";

    @Test
    void testConstructorWithArguments() {
        RegisterRequest registerRequest = new RegisterRequest(testUsername, testPassword, "John", "Doe", "USA");
        assertEquals(testUsername, registerRequest.getUsername());
        assertEquals(testPassword, registerRequest.getPassword());
        assertEquals("John", registerRequest.getFirstname());
        assertEquals("Doe", registerRequest.getLastname());
        assertEquals("USA", registerRequest.getCountry());
    }

    @Test
    void testConstructorWithoutArguments() {
        RegisterRequest registerRequest = new RegisterRequest();
        assertNull(registerRequest.getUsername());
        assertNull(registerRequest.getPassword());
        assertNull(registerRequest.getFirstname());
        assertNull(registerRequest.getLastname());
        assertNull(registerRequest.getCountry());
    }

    @Test
    void testSetters() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(testUsername);
        registerRequest.setPassword(testPassword);
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setCountry("USA");

        assertEquals(testUsername, registerRequest.getUsername());
        assertEquals(testPassword, registerRequest.getPassword());
        assertEquals("John", registerRequest.getFirstname());
        assertEquals("Doe", registerRequest.getLastname());
        assertEquals("USA", registerRequest.getCountry());
    }

    @Test
    void testGetters(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(testUsername);
        registerRequest.setPassword(testPassword);
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");

        assertEquals(testUsername, registerRequest.getUsername());
        assertEquals(testPassword, registerRequest.getPassword());
        assertEquals("John", registerRequest.getFirstname());
        assertEquals("Doe", registerRequest.getLastname());
    }

    @Test
    void testBuilder(){
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(testUsername)
                .password(testPassword)
                .firstname("John")
                .lastname("Doe")
                .country("USA")
                .build();

        assertEquals(testUsername, registerRequest.getUsername());
        assertEquals(testPassword, registerRequest.getPassword());
        assertEquals("John", registerRequest.getFirstname());
        assertEquals("Doe", registerRequest.getLastname());
        assertEquals("USA", registerRequest.getCountry());
    }
}