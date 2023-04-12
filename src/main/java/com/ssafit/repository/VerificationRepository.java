package com.ssafit.repository;

import com.ssafit.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByEmail(final String email);
    Optional<Verification> findByCode(final String code);
}
