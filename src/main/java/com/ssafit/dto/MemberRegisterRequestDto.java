package com.ssafit.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Builder
@RequiredArgsConstructor
public class MemberRegisterRequestDto {
    private final String email;
    private final String password;
    private final String nickname;
    private final String studentId;
    private final String phone;
    private final String image;
    private final boolean admin;
}