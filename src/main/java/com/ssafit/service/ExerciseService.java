package com.ssafit.service;

import com.ssafit.domain.Exercise;
import com.ssafit.dto.Exercise.*;
import com.ssafit.exception.ExerciseException;
import com.ssafit.exception.status.ExerciseStatus;
import com.ssafit.repository.ExerciseRepository;
import com.ssafit.vo.exercise.ExerciseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public boolean checkExerciseTitleDuplication(final String uuid) {
        return exerciseRepository.findByUuid(uuid).isPresent();
    }

    @Transactional
    public ExerciseRegisterResponseDto registerExercise(final ExerciseRegisterRequestDto requestDto) {
        if (checkExerciseTitleDuplication(requestDto.getTitle())) {
            throw new ExerciseException(ExerciseStatus.EXISTING_YOUTUBE);
        }

        Exercise exercise = Exercise.builder()
                .title(requestDto.getTitle())
                .url(requestDto.getUrl())
                .fitPartName(requestDto.getFitPartName())
                .youtubeId(requestDto.getYoutubeId())
                .channelName(requestDto.getChannelName())
                .viewCnt(0)
                .build();

        final Exercise result = exerciseRepository.save(exercise);

        return ExerciseRegisterResponseDto.builder()
                .title(result.getTitle())
                .build();
    }

    public ExerciseReadResponseDto readExercise(final String uuid) {
        final Exercise exercise = exerciseRepository.findByUuid(uuid).orElseThrow(
                () -> new ExerciseException(ExerciseStatus.NOT_EXISTING_EXERCISE)
        );

        return ExerciseReadResponseDto.builder()
                .title(exercise.getTitle())
                .url(exercise.getUrl())
                .fitPartName(exercise.getFitPartName())
                .youtubeId(exercise.getYoutubeId())
                .channelName(exercise.getChannelName())
                .build();
    }

    public ExerciseListReadResponseDto readAllExerciseList(final Pageable pageable) {
        final Page<Exercise> exercisePage = exerciseRepository.findAllByOrderByViewCnt(pageable);

        return extractPageableInfos(exercisePage);
    }

    public ExerciseListReadResponseDto readExerciseList(final String fitPartName, final Pageable pageable) {
        final Page<Exercise> exercisePage = exerciseRepository.findByFitPartName(fitPartName, pageable);

        return extractPageableInfos(exercisePage);
    }

    private ExerciseListReadResponseDto extractPageableInfos(final Page<Exercise> exercisePage) {
        final List<Exercise> exerciseList = exercisePage.getContent();

        final int totalPages = exercisePage.getTotalPages();

        final List<ExerciseVo> exercises = new ArrayList<>();

        for (final Exercise exercise : exerciseList) {
            ExerciseVo exerciseVo = ExerciseVo.builder()
                    .title(exercise.getTitle())
                    .url(exercise.getUrl())
                    .fitPartName(exercise.getFitPartName())
                    .youtubeId(exercise.getYoutubeId())
                    .channelName(exercise.getChannelName())
                    .build();

            exercises.add(exerciseVo);
        }

        return ExerciseListReadResponseDto.builder()
                .exercises(exercises)
                .totalPages(totalPages)
                .build();
    }

    @Transactional
    public ExerciseDeleteResponseDto deleteExercise(final String uuid) {
        final Exercise exercise = exerciseRepository.findByUuid(uuid).orElseThrow(
                () -> new ExerciseException(ExerciseStatus.NOT_EXISTING_EXERCISE)
        );

        exerciseRepository.delete(exercise);

        return ExerciseDeleteResponseDto.builder()
                .title(exercise.getTitle())
                .build();
    }
}
