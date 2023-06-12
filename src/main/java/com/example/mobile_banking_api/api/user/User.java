package com.example.mobile_banking_api.api.user;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private Integer id;
    private String name;
    private String gender;
    private String oneSignalId;
    private String studentCardId;
    private Boolean isStudent;
    private Boolean isDeleted;

    //Auth
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;

    //User has role
    private List<Role> roles;

}
