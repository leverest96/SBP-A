package com.ssafit.exception;

import com.ssafit.exception.status.VerificationStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationException extends RuntimeException{
    private final VerificationStatus status;
}
