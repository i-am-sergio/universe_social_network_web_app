package com.unsa.backend.configtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

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

    @DisplayName("TestInitMethod")
    @Test
    void testInitMethod_createsUploadDirectory() throws Exception {
        controller.init();

        String uploadDir = (String) ReflectionTestUtils.getField(controller, "uploadDir");
        Path directoryPath = Paths.get(uploadDir);
        assertTrue(Files.exists(directoryPath) && Files.isDirectory(directoryPath));
    }

    @DisplayName("TestHandleFileUploadEmptyFile")
    @Test
    void testHandleFileUpload_emptyFile() throws Exception {
        MultipartFile emptyFile = new MockMultipartFile("file", "filename.txt", "text/plain", new byte[0]);

        ResponseEntity<String> response = controller.handleFileUpload(emptyFile, "filename.txt");

        assertEquals("Please select a file", response.getBody());
    }

}

