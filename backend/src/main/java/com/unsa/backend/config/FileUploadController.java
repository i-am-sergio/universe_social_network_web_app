package com.unsa.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path targetPath;

    @PostConstruct
    public void init() throws IOException {
        targetPath = new File(uploadDir).toPath().normalize();
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file");
            }

            String fullFileName = name.replaceAll("[^a-zA-Z0-9.-]", "_");

            Path filePath = targetPath.resolve(fullFileName);

            if (!filePath.normalize().startsWith(targetPath)) {
                return ResponseEntity.status(400).body("Invalid file path");
            }
            
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }
}
