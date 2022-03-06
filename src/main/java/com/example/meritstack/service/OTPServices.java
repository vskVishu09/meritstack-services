package com.example.meritstack.service;

import com.example.meritstack.exception.BadRequestException;
import com.example.meritstack.exception.NotAcceptableException;
import com.example.meritstack.model.OTPProvider;
import com.example.meritstack.repository.VerificationOTPRepository;
import com.example.meritstack.util.CalenderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class OTPServices {

    @Autowired
    VerificationOTPRepository otpRepository;

    public void validateOTP(String email, String otp) {

        Optional<OTPProvider> verificationOTPOp = otpRepository.findByEmail(email);
        if(!verificationOTPOp.isPresent()) {
            throw new BadRequestException("Something went wrong, Please regenrate OTP");
        }

        OTPProvider verificationOTP = verificationOTPOp.get();

        boolean expired = CalenderUtility.isOTPExpired(verificationOTP.getExpireAt());

        if(expired) {
            throw new NotAcceptableException("OTP has Expired");
        }

        if(otp == null || !otp.equals(verificationOTP.getOtp())) {
            throw new NotAcceptableException("Wrong OTP");
        }
    }

    public void expireOTP(String email) {
        Optional<OTPProvider> verificationOTPOp = otpRepository.findByEmail(email);
        if(!verificationOTPOp.isPresent()) {
            throw new BadRequestException("Something went wrong");
        }

        OTPProvider verificationOTP = verificationOTPOp.get();
        Calendar cal = Calendar.getInstance();
        verificationOTP.setExpireAt(cal.getTime());

        otpRepository.save(verificationOTP);
    }
}
