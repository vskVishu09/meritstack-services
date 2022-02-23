package com.example.meritstack.payload;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class OTPRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String userType;
}