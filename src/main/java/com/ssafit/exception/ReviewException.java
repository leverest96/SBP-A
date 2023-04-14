package com.ssafit.exception;

import com.ssafit.exception.status.ReviewStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewException extends RuntimeException {
    private final ReviewStatus status;
}
