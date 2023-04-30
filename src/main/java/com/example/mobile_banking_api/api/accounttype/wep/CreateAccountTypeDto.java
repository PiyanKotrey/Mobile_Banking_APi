package com.example.mobile_banking_api.api.accounttype.wep;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountTypeDto(@NotBlank(message = "Name Req!") String name) {
}
