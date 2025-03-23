package com.tms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private  final Path ROOT_PATH_FILE = Paths.get("data");

    public Boolean uploadFile(MultipartFile file) {
        try {
            if (file.getOriginalFilename() == null) {
                return false;
            }
            Files.copy(file.getInputStream(),  ROOT_PATH_FILE.resolve(file.getOriginalFilename()));
        }catch (IOException e) {
            return false;
        }
        return true;
    }

}
