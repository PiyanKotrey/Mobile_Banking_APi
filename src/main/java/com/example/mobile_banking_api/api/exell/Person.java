package com.example.mobile_banking_api.api.exell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String role;
    private Integer score;
    private String image;
}
