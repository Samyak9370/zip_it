package com.example.zip_it;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class FileUploadLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;
    private String contentType;
    private long size;
    private LocalDateTime uploadTimestamp;

    // Constructors
    public FileUploadLog() {
    }

    public FileUploadLog(String originalFilename, String contentType, long size) {
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.uploadTimestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    // (You can generate these in your IDE: Right-click -> Generate -> Getters and Setters)
    public Long getId() { 
        return id; }
    public void setId(Long id) { 
        this.id = id; }
    public String getOriginalFilename() {
         return originalFilename; }
    public void setOriginalFilename(String fn) { 
        this.originalFilename = fn; }
    public String getContentType() { return contentType; }
    public void setContentType(String ct) { this.contentType = ct; }
    public long getSize() { return size; }
    public void setSize(long s) { this.size = s; }
    public LocalDateTime getUploadTimestamp() { return uploadTimestamp; }
    public void setUploadTimestamp(LocalDateTime ts) { this.uploadTimestamp = ts; }
}