package com.ssafit.dto.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class ReviewRegisterRequestDto {
    private final String exerciseUuid;
    private final String title;
    private final String content;
}
