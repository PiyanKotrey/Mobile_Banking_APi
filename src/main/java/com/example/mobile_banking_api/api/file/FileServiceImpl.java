package com.example.mobile_banking_api.api.file;

import com.example.mobile_banking_api.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


@Service
public class FileServiceImpl implements FileService {
    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.base-url}")
    private String fileBaseUrl;
    @Value("${file.download-url}")
    private String fileDownloadUrl;
    @Value("${file.download-url-zip}")
    private String fileDLZip;
    private FileUtil fileUtil;

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return fileUtil.upload(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        List<FileDto> filesDto = new ArrayList<>();
        for (MultipartFile file : files) {
            filesDto.add(fileUtil.upload(file));
        }
        return filesDto;
    }

    @Override
    public void deleteByName(String fileName) {
        Path path = Paths.get(fileServerPath + fileName);
        try {
            Boolean isDelete = Files.deleteIfExists(path);
            if (!isDelete) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File name is not found!");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Delete file failed!");
        }
    }

    @Override
    public void deleteAllFile(String directory) {
        try (Stream<Path> paths = Files.list(Paths.get(directory))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete file.");
                        }
                    });
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to list files.");
        }
    }

    public List<FileDto> findFile(String directory) {
        List<FileDto> fileDtos = new ArrayList<>();
        try (Stream<Path> paths = Files.list(Paths.get(directory))) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                String fileName = path.getFileName().toString();
                String fileUrl = String.format("%s%s", fileBaseUrl, fileName);
                String downloadUrl = String.format("%s%s", fileDownloadUrl, fileName);
                String downloadZip = String.format("%s",fileDLZip);
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                long fileSize;
                try {
                    fileSize = Files.size(path);
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to get file size.");
                }
                fileDtos.add(FileDto.builder()
                        .name(fileName)
                        .url(fileUrl)
                        .downloadUrl(downloadUrl)
                        .downloadAllImagesZip(downloadZip)
                        .extension(fileExtension)
                        .size(fileSize)
                        .build());
            });
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to list files.");
        }
        return fileDtos;
    }

    @Override
    public FileDto findFileByName(String directory, String fileName) {
        try (Stream<Path> paths = Files.list(Paths.get(directory))) {
            Optional<Path> optionalPath = paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(fileName))
                    .findFirst();
            if (optionalPath.isPresent()) {
                Path path = optionalPath.get();
                String fileUrl = String.format("%s%s", fileBaseUrl, fileName);
                String downloadUrl = String.format("%s%s", fileDownloadUrl, fileName);
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                long fileSize = Files.size(path);
                return FileDto.builder()
                        .name(fileName)
                        .url(fileUrl)
                        .downloadUrl(downloadUrl)
                        .extension(fileExtension)
                        .size(fileSize)
                        .build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to list files.");
        }
    }
    @Override
    public ResponseEntity<Resource> downloadFileByName(String fileName) throws IOException {
        Path path = Paths.get(fileServerPath + fileName);
        Resource resource = new FileSystemResource(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; file\"" + fileName + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }


}






