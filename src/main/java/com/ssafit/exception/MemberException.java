package com.ssafit.exception;

import com.ssafit.exception.status.MemberStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberException extends RuntimeException {
    private final MemberStatus status;
}