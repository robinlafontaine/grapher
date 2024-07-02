package io.lafontaine.grapher;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Upload {

    private static final String UPLOAD_DIRECTORY = "uploads";

    public static void uploadFile(MultipartFile file) throws IOException {
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
    }

    public static void deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIRECTORY, fileName);
        Files.delete(filePath);
    }

}
