package com.ssafit.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class ReviewReadResponseDto {
    private final String uuid;
    private final String nickname;
    private final String title;
    private final String content;
    private final int viewCnt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdDate;
}
