package com.ssafit.dto.Member;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class MemberLoginResponseDto {
    private final String accessToken;
    private final String refreshToken;
}