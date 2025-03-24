package com.tms.controller;

import com.tms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //Upload file
    @PostMapping
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {
        Boolean result = fileService.uploadFile(file);
        return new ResponseEntity<>(result ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    //Download file
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Optional<Resource> resource = fileService.getFile(filename);
        if (resource.isPresent()) {
           HttpHeaders headers = new HttpHeaders();
           headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.get().getFilename());
           return new ResponseEntity<>(resource.get(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Download list files
    @GetMapping
    public ResponseEntity<ArrayList<String>> getListOfFiles() {
        ArrayList<String> files;
        try {
            files = fileService.getListOfFiles();
        } catch (IOException e) {
            System.out.println("Files not found -> " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    //Delete file
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("filename") String filename) {
        Boolean result = fileService.deleteFileService(filename);
        return new ResponseEntity<>(result ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

}