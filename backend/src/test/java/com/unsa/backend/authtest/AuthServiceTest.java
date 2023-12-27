package com.unsa.backend.authtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.unsa.backend.auth.AuthService;
import com.unsa.backend.auth.AuthUserResponse;
import com.unsa.backend.auth.LoginRequest;
import com.unsa.backend.auth.RegisterRequest;
import com.unsa.backend.jwt.JwtService;
import com.unsa.backend.users.UserModel;
import com.unsa.backend.users.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test Auth")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @MockBean
    private UserRepository userRepository;

    private AuthService authService;

    @Autowired
    public AuthServiceTest(AuthService authService) {
        this.authService = authService;
    }

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private static final String USER = "usuario";


    @DisplayName("Test valid login")
    @Test
    void testSuccessfulLogin() {
        LoginRequest validRequest = new LoginRequest(USER, "contrasena");
        UserModel userModel = new UserModel();
        userModel.setUsername(validRequest.getUsername());
        userModel.setPassword("hashedPassword");
        String expectedToken = "token";
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(validRequest.getUsername(), validRequest.getPassword()));
        when(userRepository.findByUsername(validRequest.getUsername())).thenReturn(Optional.of(userModel));
        UserDetails userDetails = userModel;
        when(jwtService.getToken(userDetails)).thenReturn(expectedToken);

        AuthUserResponse authUserResponse = authService.login(validRequest);
        assertNotNull(authUserResponse);
        assertEquals(USER, authUserResponse.getUsername());
        assertEquals(expectedToken, authUserResponse.getToken());

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByUsername(validRequest.getUsername());
        verify(jwtService, times(1)).getToken(userDetails);
    }

    @Test
    void testFailedLogin() {
        LoginRequest invalidRequest = new LoginRequest(USER, "contrasena_incorrecta");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Bad Credentials"));
        AuthUserResponse authUserResponse;
        try{
            authUserResponse = authService.login(invalidRequest);
        }catch(Exception e){
            authUserResponse = null;
        }
        assertNull(authUserResponse);
        verify(authenticationManager, times(1)).authenticate(any());
        verifyNoMoreInteractions(userRepository, jwtService);
    }

    @Test
    void testSuccessfulRegistration() {
        RegisterRequest validRequest = new RegisterRequest("nuevoUsuario", "contrasenaNueva", "Nombre", "Apellido","Yugoslavia");
        AuthUserResponse authUserResponse = authService.register(validRequest);
        assertNotNull(authUserResponse);
    }
}   