package com.unsa.backend.config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestBody FileData fileData) {
        try {
            /*
             * Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
             * "cloud_name", "djryb80lo",
             * "api_key", "561793922874873",
             * "api_secret", "tejlK2g0EtC0bJwS-18XUve51ac"));
             * cloudinary.uploader().upload(
             * new
             * File("https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg"),
             * ObjectUtils.asMap("public_id", "olympic_flag"));
             */
            String name = fileData.getName();
            MultipartFile file = fileData.getFile();
            System.out.println("name: " + name);
            /*
             * File directory = new File(uploadDir);
             * if (!directory.exists()) {
             * directory.mkdirs();
             * }
             * 
             * String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
             * String fileExtension =
             * originalFileName.substring(originalFileName.lastIndexOf('.'));
             * 
             * String fullFileName = name + fileExtension;
             * System.out.println("fullFileName: " + fullFileName);
             * 
             * File uploadedFile = new File(directory.getAbsolutePath() + File.separator +
             * fullFileName);
             * file.transferTo(uploadedFile);
             */
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file");
        }
    }
}