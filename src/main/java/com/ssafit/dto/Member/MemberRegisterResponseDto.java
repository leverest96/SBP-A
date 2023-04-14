package com.ssafit.dto.Member;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class MemberRegisterResponseDto {
    private final String nickname;
}
