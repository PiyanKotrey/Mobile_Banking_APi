package com.example.mobile_banking_api.api.file;

import com.example.mobile_banking_api.base.BaseRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/files")
@Slf4j
@RequiredArgsConstructor
public class FileRestController {
    private final FileService fileService;

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

    @DeleteMapping("/{fileName}")
    public BaseRest<?> delete(@PathVariable String fileName) {
        String filename = fileService.delete(fileName);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("File Have Been Upload Success")
                .timestamp(LocalDateTime.now())
                .data(filename)
                .build();
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
}
