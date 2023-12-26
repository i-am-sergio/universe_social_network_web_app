package com.unsa.backend.configtest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TestUploadController")
@ExtendWith(MockitoExtension.class)
class FileUploadControllerTest {
    private static final String IMAGE = "test.txt";
    private static final String TYPE = "text/plain";
    private static final String URL_BASE = "/upload";

    @MockBean
    private Cloudinary cloudinary;

    private MockMvc mockMvc;

    @Autowired
    void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Test case for upload a file to cloudinary from the Controller.
     */
    @DisplayName("Test upload file")
    @Test
    void testUploadFile() throws Exception {
        byte[] fileContent = "Archivo de prueba".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", IMAGE, TYPE, fileContent);
        Uploader uploaderMock = mock(Uploader.class);
        Map<String, String> cloudinaryResponse = new HashMap<>();
        cloudinaryResponse.put("url", "https://cloudinary.com/your_image.jpg");
        when(cloudinary.uploader()).thenReturn(uploaderMock);
        when(uploaderMock.upload(any(byte[].class), any())).thenReturn(cloudinaryResponse);
        mockMvc.perform(multipart(URL_BASE)
                .file(mockMultipartFile)
                .param("name", IMAGE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("https://cloudinary.com/your_image.jpg")));
        verify(uploaderMock, times(1)).upload(any(byte[].class), any());
    }

    /**
     * Test case for upload a file to cloudinary from the Controller and dont select
     * a File.
     */
    @DisplayName("Test upload file without select a file")
    @Test
    void testUploadFileWithoutSelectFile() throws Exception {
        MockMultipartFile mockEmptyFile = new MockMultipartFile("file", new byte[0]);
        mockMvc.perform(multipart(URL_BASE)
                .file(mockEmptyFile)
                .param("name", "empty_file.txt"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Please select a file"));
    }

    /**
     * Test case for upload a file to cloudinary from the Controller and the name
     * doesn't have a dot.
     */
    @DisplayName("Test upload file without dot in the name")
    @Test
    void testUploadFileWithoutDotInTheName() throws Exception {
        byte[] fileContent = "image".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test", TYPE, fileContent);
        mockMvc.perform(multipart(URL_BASE)
                .file(mockMultipartFile)
                .param("name", "test"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("File name doesn't contain an extension"));
    }

    /**
     * Test case for upload a file to cloudinary from the Controller adn get error
     * 500.
     */
    @DisplayName("Test upload file with error 500")
    @Test
    void testUploadFileWithIOException() throws Exception {
        byte[] fileContent = "Archivo de prueba".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", IMAGE, TYPE, fileContent);

        Uploader uploaderMock = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploaderMock);
        when(uploaderMock.upload(any(byte[].class), any())).thenThrow(IOException.class);
        mockMvc.perform(multipart(URL_BASE)
                .file(mockMultipartFile)
                .param("name", IMAGE))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error uploading file"));
    }

}
