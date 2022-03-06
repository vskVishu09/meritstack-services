package com.example.meritstack.controller.company;

import com.example.meritstack.exception.BadRequestException;
import com.example.meritstack.model.AuthProvider;
import com.example.meritstack.model.User;
import com.example.meritstack.payload.requests.company.CompSignUpRequest;
import com.example.meritstack.payload.responses.ApiResponse;
import com.example.meritstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/company")
public class CompAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup/user")
    public ResponseEntity<?> registerCompUser(@Valid @RequestBody CompSignUpRequest compSignUpRequest) {
        if (userRepository.existsByEmail(compSignUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = new User();
        user.setFirstName(compSignUpRequest.getFirstName());
        user.setLastName(compSignUpRequest.getLastName());
        user.setPhone(compSignUpRequest.getPhone());
        user.setEmail(compSignUpRequest.getEmail());
        user.setPassword(compSignUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setUserType(compSignUpRequest.getUserType());
        user.setCompanyName(compSignUpRequest.getCompanyName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Company User registered successfully"));
    }
}
