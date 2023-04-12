package com.ssafit.exception.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VerificationStatus {
    UNVERIFIED(HttpStatus.CONFLICT, "unverified"),
    NOT_EXISTING_CODE(HttpStatus.CONFLICT, "not existing verification code"),
    NOT_EXISTING_EMAIL(HttpStatus.CONFLICT, "not existing verification email");

    private final HttpStatus httpStatus;
    private final String message;
}
