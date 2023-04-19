package com.ssafit.dto.Exercise;

import com.ssafit.vo.exercise.ExerciseVo;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class ExerciseListReadResponseDto {
    private final List<ExerciseVo> exercises;
}
