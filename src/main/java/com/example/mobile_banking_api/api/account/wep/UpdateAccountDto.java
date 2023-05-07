package com.example.mobile_banking_api.api.account.wep;

import lombok.Builder;

@Builder
public record UpdateAccountDto(
        String profile,
        Integer pin,
        String password,
        Integer phoneNumber,
        Integer transferLimit,
        Integer accountType
) {
}
