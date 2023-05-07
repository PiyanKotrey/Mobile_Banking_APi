package com.example.mobile_banking_api.api.account.wep;

import lombok.Builder;

@Builder
public record CreateAccDto(        Integer accountNo,
                                   String accountName,
                                   String profile,
                                   Integer pin,
                                   String password,
                                   Integer phoneNumber,
                                   Integer transferLimit,
                                   Integer accountType) {
}
