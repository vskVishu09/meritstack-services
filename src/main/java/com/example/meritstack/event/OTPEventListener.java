package com.example.meritstack.event;

import com.example.meritstack.model.OTPProvider;
import com.example.meritstack.model.User;
import com.example.meritstack.security.mail.MailSender;
import com.example.meritstack.service.VerificationOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OTPEventListener implements ApplicationListener<OTPEvent> {

    @Autowired
    MailSender mailSender;

    @Autowired
    private VerificationOTPService verificationOTPService;

    @Override
    public void onApplicationEvent(OTPEvent event) {
        this.sendOtp(event);
    }

    private void sendOtp(OTPEvent event) {
        User user = event.getUser();
        OTPProvider verificationOTP = verificationOTPService.createOTP(user.getEmail());

        String recipientAddress = user.getEmail();
        String subject = "Meritstack: Reset Password";
        Long otp = verificationOTP.getOtp();
        String message = "Reset your password using OTP: " + otp;
        String from = "support.merit@gmail.com";
        String signature = "Thanks & Regards, \n Team Meritstack.";
        mailSender.sendEmail(from, recipientAddress, null, null, subject, message, signature);
    }
}
