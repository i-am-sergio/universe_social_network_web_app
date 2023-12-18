package com.unsa.backend.config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path targetPath;

    private final Cloudinary cloudinary;

    public FileUploadController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @PostConstruct
    public void init() throws IOException {
        targetPath = new File(uploadDir).toPath().normalize();
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam MultipartFile file,
            @RequestParam String name) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file");
            }

            String fileNameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
            String fullFileName = fileNameWithoutExtension.replaceAll("[^a-zA-Z0-9.-]", "_");

            Path filePath = targetPath.resolve(fullFileName);

            if (!filePath.normalize().startsWith(targetPath)) {
                return ResponseEntity.status(400).body("Invalid file path");
            }

            return ResponseEntity.ok(cloudinary.uploader().upload(file.getBytes(),
                    Map.of("public_id", fullFileName))
                    .get("url").toString());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }
}