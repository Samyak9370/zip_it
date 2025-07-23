package com.example.zip_it;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadLogRepository extends JpaRepository<FileUploadLog, Long> {
}