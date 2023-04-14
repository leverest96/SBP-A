package com.ssafit.exception;

import com.ssafit.exception.status.ExerciseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExerciseException extends RuntimeException {
    private final ExerciseStatus status;
}
