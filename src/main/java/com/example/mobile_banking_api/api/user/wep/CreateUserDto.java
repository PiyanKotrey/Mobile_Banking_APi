package com.example.mobile_banking_api.api.user.wep;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserDto(@NotBlank(message = "Name is Rq!") String name,
                            @NotBlank(message = "Gender is Rq!") String gender,
                            String oneSignalId,
                            String studentCardId,
                            @NotNull(message = "Are you Student?" ) Boolean isStudent) {
}
