package com.ssafit.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExerciseStatus {
    EXISTING_YOUTUBE(HttpStatus.CONFLICT, "must not be existing youtube"),
    NOT_EXISTING_EXERCISE(HttpStatus.NOT_FOUND, "must be an exsiting exercise"),
    NOT_AUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "must be an authorized member");

    private final HttpStatus httpStatus;
    private final String message;
}
