package com.example.mobile_banking_api.api.auth.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record LoginDto(@Email
                       @NotBlank
                       String email,
                        @NotBlank
                        String password) {
}
