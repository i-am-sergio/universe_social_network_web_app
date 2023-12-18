package com.unsa.backend.apptest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.BackendApplication;

@SpringBootTest
@DisplayName("Backend Application Test")
class BackendApplicationTest {
    
    /*
     * Verifica que la aplicación se cargue correctamente
    */ 
    @Test
    void contextLoads() {
        BackendApplication.main(new String[]{});
        assertNotNull(BackendApplication.class);
    }
}
