package io.lafontaine.grapher;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Upload {

    public static void tmpSaveFile(MultipartFile file, String upload_dir) throws IOException {
        Path fileNameAndPath = Paths.get(upload_dir, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
    }

    public static void validateFileSafety(MultipartFile file) {
        // Check if file is a safe json file
        String filename = file.getOriginalFilename();
        if (!filename.endsWith(".json")) {
            throw new IllegalArgumentException("File must be a json file");
        }
    }

    public static boolean isValidJson(String json) {
        try {
            Graph.fromJson(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }







}
