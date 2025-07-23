package com.example.zip_it;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ZipController {

    // Use private final for the repository
    private final FileUploadLogRepository fileUploadLogRepository;

    // --- FIX #2: Use Constructor Injection ---
    // Spring will automatically provide the repository when creating the controller.
    public ZipController(FileUploadLogRepository fileUploadLogRepository) {
        this.fileUploadLogRepository = fileUploadLogRepository;
    }

    @GetMapping("/")
    public String showUploadForm() {
        return "index";
    }

    @PostMapping("/zip-files")
    public ResponseEntity<Resource> zipFiles(@RequestParam("files") MultipartFile[] files) throws IOException {

        // --- FIX #1: VALIDATE FIRST ---
        // Check for empty files at the very beginning of the method.
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body(null);
        }

        // Now that we know files exist, log them.
        Arrays.stream(files).forEach(file -> {
            FileUploadLog log = new FileUploadLog(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize()
            );
            fileUploadLogRepository.save(log);
        });

        // The rest of your zipping logic is perfect.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (MultipartFile file : files) {
                ZipEntry entry = new ZipEntry(file.getOriginalFilename());
                zos.putNextEntry(entry);
                zos.write(file.getBytes());
                zos.closeEntry();
            }
        }

        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"archive.zip\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}