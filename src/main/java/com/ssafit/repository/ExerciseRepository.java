package com.ssafit.repository;

import com.ssafit.domain.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByUuid(final String uuid);
    Optional<Exercise> findByTitle(final String title);

    Page<Exercise> findByFitPartName(final String fitPartName, final Pageable pageable);

    Page<Exercise> findAllByOrderByViewCnt(final Pageable pageable);
}
