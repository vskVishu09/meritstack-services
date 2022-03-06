package com.example.meritstack.controller;

import com.example.meritstack.event.OTPEvent;
import com.example.meritstack.exception.BadRequestException;
import com.example.meritstack.exception.NotAcceptableException;
import com.example.meritstack.model.AuthProvider;
import com.example.meritstack.model.User;
import com.example.meritstack.payload.requests.LoginRequest;
import com.example.meritstack.payload.requests.OTPRequest;
import com.example.meritstack.payload.requests.ResetPasswordRequest;
import com.example.meritstack.payload.requests.SignUpRequest;
import com.example.meritstack.payload.responses.ApiResponse;
import com.example.meritstack.payload.responses.AuthResponse;
import com.example.meritstack.repository.UserRepository;
import com.example.meritstack.security.TokenProvider;
import com.example.meritstack.security.UserPrincipal;
import com.example.meritstack.service.OTPServices;
import com.example.meritstack.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.context.ApplicationEventPublisher;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    UserValidationService userValidationService;

    @Autowired
    OTPServices otpServices;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!loginRequest.getType().equalsIgnoreCase(userPrincipal.getUserType())) {
            throw new BadRequestException("Bad credentials");
        }

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhone(signUpRequest.getPhone());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setUserType(signUpRequest.getUserType());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/sendOTP")
    public ResponseEntity<?> sendOTP(@Valid @RequestBody OTPRequest otpRequest) {

        User user = userValidationService.validateAndGetUser(otpRequest.getEmail(), otpRequest.getUserType());

        eventPublisher.publishEvent(new OTPEvent(user));

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "OTP has sent to your email successfully"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        User user = userValidationService.validateAndGetUser(request.getEmail(), request.getUserType());

        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();

        if(newPassword==null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            throw new NotAcceptableException("New password is not matching with confirmed password");
        }

        otpServices.validateOTP(request.getEmail(), request.getOtp());

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpServices.expireOTP(request.getEmail());

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Password reset has done successfully, Please relogin with new password"));
    }
}
