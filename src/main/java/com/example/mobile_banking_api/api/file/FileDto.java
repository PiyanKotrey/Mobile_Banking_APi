package com.example.mobile_banking_api.api.file;

import lombok.Builder;

@Builder
public record FileDto(String name,
                      String url,
                      String downloadUrl,
                      String downloadAllImagesZip,
                      String extension,
                      long size) {
}
