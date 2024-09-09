package com.jeremias.uploaderfiles.services.impl;

import com.jeremias.uploaderfiles.services.FileUploaderService;
import jakarta.annotation.PostConstruct;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;

@Service
public class FileUploaderServiceImpl implements FileUploaderService {
    @Value("${storage.directory}")
    private String storagePath;
    @Value("${aws.bucket.s3.name}")
    private String bucketName;
    private final S3TransferManager transferManager;
    private static String STORAGE_DIRECTORY;

    public FileUploaderServiceImpl(S3TransferManager transferManager) {
        this.transferManager = transferManager;
    }

    @PostConstruct
    public void init() {
        STORAGE_DIRECTORY = storagePath;
    }

    @Override
    public void fileUpload(MultipartFile file) throws IOException {
        File targetFile = getFile(randomNumberName().concat(Objects.requireNonNull(file.getOriginalFilename())));
        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public File fileDownload(String fileName) {
        File targetFile = getFile(fileName);
        if (!targetFile.exists()) System.out.println(targetFile.toPath());
        return targetFile;
    }

    @Override
    public String awsFileUpload(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("aws-upload:", file.getOriginalFilename()); //Generate a new temp File with aws-upload prefix
        file.transferTo(tempFile); // then move all metadata from file(multipart) to my tempFile.
        var uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(req -> req.bucket(bucketName).key(file.getOriginalFilename()))
                .source(tempFile)
                .build();
        transferManager.uploadFile(uploadFileRequest);
        return "Success!";
    }

    @Override
    public String awsFileDownload(String fileName) {
        var downloadFileRequest = DownloadFileRequest.builder()
                .getObjectRequest(req -> req.bucket(bucketName).key(fileName))
                .destination(Paths.get(STORAGE_DIRECTORY, fileName))
                .build();
        transferManager.downloadFile(downloadFileRequest);
        return "Success!";
    }

    /**
     * @param fileName The name of the file that is upload or download
     * @return The file
     * @throws SecurityException If somebody tries to change the application path
     */
    private static File getFile(String fileName) {
        File targetFile = new File(STORAGE_DIRECTORY + File.separator + fileName);
        if (!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Sorry! We got some issues trying to save your file!");
        }
        return targetFile;
    }

    /**
     * @return a random number that is appended in the file name
     */
    private static String randomNumberName() {
        StringBuilder count = new StringBuilder(9);
        for (int i = 0; i < 9; i++) {
            count.append(new Random().nextInt(9));
        }
        return count.toString().concat("-");
    }

}
