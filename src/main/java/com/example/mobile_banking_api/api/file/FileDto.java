package com.example.mobile_banking_api.api.file;

import lombok.Builder;

@Builder
public record FileDto(String name,
                      String url,
                      String extension,
                      long size) {
}
