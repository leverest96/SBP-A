package com.ssafit.dto.Member;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class MemberLoginRequestDto {
    private final String email;
    private final String password;
}