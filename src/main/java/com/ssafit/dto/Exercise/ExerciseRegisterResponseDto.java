package com.ssafit.dto.Exercise;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class ExerciseRegisterResponseDto {
    private final String title;
}
