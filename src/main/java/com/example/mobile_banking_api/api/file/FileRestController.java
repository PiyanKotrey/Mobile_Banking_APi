package com.example.mobile_banking_api.api.file;

import com.example.mobile_banking_api.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@RequestMapping("api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;
    @Value("${file.server-path}")
    private String fileServerPath;


    @PostMapping
    public BaseRest<?> uploadSing(@RequestPart MultipartFile file) {
        log.info("File Rq = {}", file);
        FileDto fileDto = fileService.uploadSingle(file);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Have Been Upload Success")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @PostMapping("/multiple")
    public BaseRest<?> uploadMultiple(@RequestPart List<MultipartFile> files) {
        log.info("File Rq = {}", files);
        List<FileDto> filesDto = fileService.uploadMultiple(files);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Have Been Upload Success")
                .timestamp(LocalDateTime.now())
                .data(filesDto)
                .build();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fileName}")
    public void delete(@PathVariable String fileName) {
        fileService.deleteByName(fileName);
    }

    @GetMapping
    public BaseRest<?> findFile() {
        String directory = "D:\\FileUploadSingle\\";
        List<FileDto> fileDto = fileService.findFile(directory);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Have Been Find Success")
                .timestamp(LocalDateTime.now())
                .data(fileDto)
                .build();
    }

    @GetMapping("/{fileName}")
    public BaseRest<?> findFileByName(@PathVariable String fileName) {
        String directory = "D:\\FileUploadSingle\\";
        FileDto getName = fileService.findFileByName(directory, fileName);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Have Been FindByName Success")
                .timestamp(LocalDateTime.now())
                .data(getName)
                .build();
    }

    @DeleteMapping
    public void deleteAllFile() {
        String directory = "D:\\FileUploadSingle\\";
        fileService.deleteAllFile(directory);
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFileByName(@PathVariable String fileName) throws IOException {
         return fileService.downloadFileByName(fileName);

    }


    @GetMapping("/compressImages")
    public ResponseEntity<Resource> compressImages() throws IOException {
        // Create a temporary zip file
        File zipFile = File.createTempFile("compressed_images", ".zip");

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            // Path to the directory containing the images
            String imagesDirectoryPath = fileServerPath;

            File imagesDirectory = new File(imagesDirectoryPath);
            File[] imageFiles = imagesDirectory.listFiles();

            // Iterate over the image files and add them to the zip file
            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    if (imageFile.isFile()) {
                        // Create a new zip entry for each image file
                        ZipEntry zipEntry = new ZipEntry(imageFile.getName());
                        zipOutputStream.putNextEntry(zipEntry);

                        // Read the image file and write it to the zip output stream
                        FileInputStream fileInputStream = new FileInputStream(imageFile);
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, bytesRead);
                        }
                        fileInputStream.close();
                        zipOutputStream.closeEntry();
                    }
                }
            }
        }

        // Serve the compressed zip file as a downloadable resource
        Path zipFilePath = zipFile.toPath();
        Resource resource = new UrlResource(zipFilePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }








}
