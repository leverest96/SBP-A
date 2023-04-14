package com.ssafit.dto.review;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class ReviewRegisterResponseDto {
    private final String uuid;
}
