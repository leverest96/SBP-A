package com.ssafit.service;

import com.ssafit.domain.Exercise;
import com.ssafit.domain.Member;
import com.ssafit.domain.Review;
import com.ssafit.dto.Exercise.*;
import com.ssafit.exception.ExerciseException;
import com.ssafit.exception.MemberException;
import com.ssafit.exception.ReviewException;
import com.ssafit.exception.status.ExerciseStatus;
import com.ssafit.exception.status.MemberStatus;
import com.ssafit.exception.status.ReviewStatus;
import com.ssafit.repository.ExerciseRepository;
import com.ssafit.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public boolean checkExerciseTitleDuplication(final String title) {
        return exerciseRepository.findByTitle(title).isPresent();
    }

    @Transactional
    public ExerciseRegisterResponseDto registerExercise(final String studentId, final ExerciseRegisterRequestDto requestDto) {
        if (checkExerciseTitleDuplication(requestDto.getTitle())) {
            throw new ExerciseException(ExerciseStatus.EXISTING_YOUTUBE);
        }

        final Member member = memberRepository.findByStudentId(studentId).orElseThrow(
                () -> new MemberException(MemberStatus.NOT_EXISTING_MEMBER)
        );

        Exercise exercise = Exercise.builder()
                .title(requestDto.getTitle())
                .url(requestDto.getUrl())
                .fitPartName(requestDto.getFitPartName())
                .youtubeId(requestDto.getYoutubeId())
                .channelName(requestDto.getChannelName())
                .member(member)
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

        updateViewCnt(uuid);

        return ExerciseReadResponseDto.builder()
                .title(exercise.getTitle())
                .url(exercise.getUrl())
                .fitPartName(exercise.getFitPartName())
                .youtubeId(exercise.getYoutubeId())
                .channelName(exercise.getChannelName())
                .build();
    }

    public ExerciseListReadResponseDto readExerciseList(final String fitPartName, final Pageable pageable) {
        final Page<Exercise> exercisePage = exerciseRepository.findByFitPartName(fitPartName, pageable);

        return extractPageableInfos(exercisePage);
    }

    public ExerciseListReadResponseDto readAllExerciseList(final Pageable pageable) {
        final Page<Exercise> exercisePage = exerciseRepository.findAllByOrderByViewCnt(pageable);

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
    protected void updateViewCnt(final String uuid) {
        final Exercise exercise = exerciseRepository.findByUuid(uuid).orElseThrow(
                () -> new ExerciseException(ExerciseStatus.NOT_EXISTING_EXERCISE)
        );

        exercise.updateViewCnt();
    }

    @Transactional
    public ExerciseDeleteResponseDto deleteExercise(final String studentId, final String exerciseUuid) {
        final Exercise exercise = exerciseRepository.findByUuid(exerciseUuid).orElseThrow(
                () -> new ExerciseException(ExerciseStatus.NOT_EXISTING_EXERCISE)
        );

        if (!exercise.getMember().getStudentId().equals(studentId)) {
            throw new ExerciseException(ExerciseStatus.NOT_AUTHORIZED_MEMBER);
        }

        exerciseRepository.delete(exercise);

        return ExerciseDeleteResponseDto.builder()
                .title(exercise.getTitle())
                .build();
    }
}
