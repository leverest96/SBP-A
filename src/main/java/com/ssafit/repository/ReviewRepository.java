package com.ssafit.repository;

import com.ssafit.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUuid(final String uuid);
    Page<Review> findByExerciseUuid(final String exerciseUuid, final Pageable pageable);
}
