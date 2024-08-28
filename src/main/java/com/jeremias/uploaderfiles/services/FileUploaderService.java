package com.jeremias.uploaderfiles.services;

import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploaderService {
    void fileUpload(MultipartFile file) throws IOException;

    File fileDownload(String fileName);
}
