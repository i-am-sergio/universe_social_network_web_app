package com.unsa.backend.configtest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.unsa.backend.config.AppConfig;
import com.unsa.backend.users.UserRepository;

@SpringBootTest
@DisplayName("Test AppConfig")
@ExtendWith(MockitoExtension.class)
class AppConfigTest {
    @MockBean
    private UserRepository userRepository;

    AppConfig appConfig;

    @Autowired
    public AppConfigTest(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /*
     * Test for userDetailsService and get UsernameNotFoundException
     */
    @Test
    @DisplayName("Test AppConfig")
    void testAppConfig() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        UserDetailsService userDetailsService = appConfig.userDetailsService();
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("username"));
    }

}
