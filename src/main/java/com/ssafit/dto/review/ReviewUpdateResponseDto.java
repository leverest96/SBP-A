package com.ssafit.dto.review;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class ReviewUpdateResponseDto {
    private final String content;
}
