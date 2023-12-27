package com.unsa.backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.Map;

@RestController
public class FileUploadController {

    private final Cloudinary cloudinary;

    public FileUploadController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam MultipartFile file,
            @RequestParam String name) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file");
            }

            int dotIndex = name.lastIndexOf('.');
            if (dotIndex == -1) {
                return ResponseEntity.badRequest().body("File name doesn't contain an extension");
            }
            String fileNameWithoutExtension = name.substring(0, dotIndex);
            String fullFileName = fileNameWithoutExtension.replaceAll("[^a-zA-Z0-9.-]", "_");
            return ResponseEntity.ok(cloudinary.uploader().upload(file.getBytes(),
                    Map.of("public_id", fullFileName))
                    .get("url").toString());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }
}