package com.jeremias.uploaderfiles.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploaderService {
    void fileUpload(MultipartFile file) throws IOException;

    File fileDownload(String fileName);

    String awsFileUpload(MultipartFile file) throws IOException;

    String awsFileDownload(String fileName);
}
