package com.example.meritstack.service;

import com.example.meritstack.model.User;
import com.example.meritstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    @Autowired
    private UserRepository userRepository;

    public User validateAndGetUser(String email, String userType) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User is not registered with email : " + email)
                );

        if(userType !=null && !userType.equalsIgnoreCase(user.getUserType())) {
            throw new UsernameNotFoundException("User is not registered as : " + userType);
        }

        return user;
    }

}
