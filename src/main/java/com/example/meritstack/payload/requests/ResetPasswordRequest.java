package com.example.meritstack.payload.requests;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is not Valid")
    private String email;

    @NotBlank(message = "New password should not be blank")
    private String newPassword;

    @NotBlank(message = "Confirm password should not be blank")
    private String confirmPassword;

    @Pattern(regexp="\\d{6}", message = "OTP should be 6 digit")
    private String otp;

    @NotBlank(message = "Usertype should not be blank")
    private String userType;

}
