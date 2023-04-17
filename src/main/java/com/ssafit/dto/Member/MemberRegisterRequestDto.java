package com.ssafit.dto.Member;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class MemberRegisterRequestDto {
    private final String email;
    private final String password;
    private final String nickname;
    private final String studentId;
    private final String phone;
    private final boolean admin;
}