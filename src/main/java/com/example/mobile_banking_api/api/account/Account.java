package com.example.mobile_banking_api.api.account;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class Account {
    private Integer id;
    private Integer accountNo;
    private String accountName;
    private String profile;
    private Integer pin;
    private String password;
    private Integer phoneNumber;
    private Integer transferLimit;
    private Integer accountType;
}
