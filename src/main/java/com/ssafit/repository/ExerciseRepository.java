package com.ssafit.repository;

import com.ssafit.domain.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByUuid(final String uuid);
    Optional<Exercise> findByTitle(final String title);
    List<Exercise> findAll();

    List<Exercise> findAllByFitPartName(final String fitPartName);

    List<Exercise> findAllByOrderByViewCnt();
}
