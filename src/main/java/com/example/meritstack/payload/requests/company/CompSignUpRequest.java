package com.example.meritstack.payload.requests.company;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CompSignUpRequest {
    @NotBlank(message = "First name should not be blank")
    private String firstName;

    @NotBlank(message = "Last name should not be blank")
    private String lastName;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is not Valid")
    private String email;

    @NotBlank(message = "Password should not be blank")
    private String password;

    @NotBlank(message = "Phone should not be blank")
    private String phone;

    @NotBlank(message = "Usertype should not be blank")
    private String userType;

    @NotBlank(message = "company name should not be blank")
    private String companyName;
}
