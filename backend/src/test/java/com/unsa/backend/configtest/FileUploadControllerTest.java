package com.unsa.backend.configtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TestUploadController")
@ExtendWith(MockitoExtension.class)
public class FileUploadControllerTest {


}

