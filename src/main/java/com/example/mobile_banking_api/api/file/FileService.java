package com.example.mobile_banking_api.api.file;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * uses to upload a single file
     *
     * @param file request from data from client
     * @return FileDto
     */
    FileDto uploadSingle(MultipartFile file);

    /**
     * uses to upload a single file
     *
     * @param files request from data from client
     * @return FileDto
     */
    List<FileDto> uploadMultiple(List<MultipartFile> files);

    void deleteByName(String fileName);

    void deleteAllFile(String directory);

    List<FileDto> findFile(String directory);

    FileDto findFileByName(String directory, String fileName);

    ResponseEntity<Resource> downloadFileByName(String fileName) throws IOException;
//    Resource download(String name);
}
