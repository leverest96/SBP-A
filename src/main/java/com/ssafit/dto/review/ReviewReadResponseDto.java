package com.ssafit.dto.review;

import com.ssafit.vo.review.ReviewWithProfileVo;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class ReviewReadResponseDto {
    private final List<ReviewWithProfileVo> reviews;
    private final int totalPages;
}
