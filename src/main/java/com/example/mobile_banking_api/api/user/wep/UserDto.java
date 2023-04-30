package com.example.mobile_banking_api.api.user.wep;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDto( String name,
                       String gender,
                       String oneSignalId,
                       Boolean isDeleted,
                       Boolean isStudent,
                       String studentCardId
                       ) {
}
