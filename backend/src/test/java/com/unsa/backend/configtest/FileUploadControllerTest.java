package com.unsa.backend.configtest;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.cloudinary.Cloudinary;
import com.unsa.backend.config.FileUploadController;

import io.jsonwebtoken.io.IOException;



@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TestUploadController")
@ExtendWith(MockitoExtension.class)
public class FileUploadControllerTest {

    private Cloudinary cloudinary;
    private MockMvc mockMvc;
    private FileUploadController controller;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() throws IOException, java.io.IOException {
        Path tempDir = Files.createTempDirectory("test-upload-dir");

        String uploadDir = tempDir.toString();

        this.cloudinary = new Cloudinary();

        this.controller = new FileUploadController(cloudinary);

        ReflectionTestUtils.setField(controller, "uploadDir", uploadDir);
    }

}

