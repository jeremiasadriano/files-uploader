package com.jeremias.uploaderfiles.services.impl;

import com.jeremias.uploaderfiles.services.FileUploaderService;
import org.apache.coyote.BadRequestException;
import org.slf4j.ILoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;

@Service
public class FileUploaderServiceImpl implements FileUploaderService {
    public static final String STORAGE_DIRECTORY = "/home/godal/ProgrammingZone/Java";

    @Override
    public void fileUpload(MultipartFile file) throws IOException {
        File targetFile = getFile(randomNumberName().concat(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public File fileDownload(String fileName) {
        File targetFile = getFile(fileName);
        if (!targetFile.exists()) System.out.println(targetFile.toPath());
        return targetFile;
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
