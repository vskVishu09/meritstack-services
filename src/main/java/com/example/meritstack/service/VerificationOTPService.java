package com.example.meritstack.service;

import java.util.Random;

import com.example.meritstack.model.OTPProvider;
import com.example.meritstack.repository.VerificationOTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VerificationOTPService {

	@Autowired
	VerificationOTPRepository otpRepository;

	public OTPProvider createOTP(String email) {
		Random random = new Random();
		String otp = String.format("%06d", random.nextInt(999999));
		OTPProvider verificationOTP = new OTPProvider(email, otp);
		verificationOTP = otpRepository.save(verificationOTP);
		return verificationOTP;
	}
}
