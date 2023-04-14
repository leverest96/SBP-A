package com.ssafit.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewStatus {
    NOT_EXISTING_REVIEW(HttpStatus.NOT_FOUND, "must be an existing review"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "must be an authorized member");

    private final HttpStatus httpStatus;
    private final String message;
}
