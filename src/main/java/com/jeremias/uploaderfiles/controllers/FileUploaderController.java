package com.jeremias.uploaderfiles.controllers;

import com.jeremias.uploaderfiles.services.FileUploaderService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileUploaderController {
    private final FileUploaderService fileUploaderService;

    public FileUploaderController(FileUploaderService fileUploaderService) {
        this.fileUploaderService = fileUploaderService;
    }

    @PostMapping
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        fileUploaderService.fileUpload(file);
        return ResponseEntity.ok("Success!");
    }

    @GetMapping
    public ResponseEntity<Resource> fileDownload(@RequestParam("filename") String fileName) {
        File file = fileUploaderService.fileDownload(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(new FileSystemResource(file));

    }
}
