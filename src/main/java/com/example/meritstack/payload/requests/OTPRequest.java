package com.example.meritstack.payload.requests;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class OTPRequest {

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is not Valid")
    private String email;

    @NotBlank(message = "Usertype should not be blank")
    private String userType;
}
