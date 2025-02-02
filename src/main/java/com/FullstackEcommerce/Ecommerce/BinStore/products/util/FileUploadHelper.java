package com.FullstackEcommerce.Ecommerce.BinStore.products.util;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadHelper {

    @Value("${upload.directory}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path targetLocation = Paths.get(uploadDir).resolve(filename);

        Files.copy(file.getInputStream(), targetLocation);

        return targetLocation.toString(); // Return the file's URL
    }
}
