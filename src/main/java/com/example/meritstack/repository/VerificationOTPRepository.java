package com.example.meritstack.repository;

import java.util.Optional;

import com.example.meritstack.model.OTPProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationOTPRepository extends JpaRepository<OTPProvider, String> {

	  Optional<OTPProvider> findByEmail(String email);
}
