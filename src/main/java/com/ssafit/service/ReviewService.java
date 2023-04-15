package com.ssafit.service;

import com.ssafit.domain.Exercise;
import com.ssafit.domain.Member;
import com.ssafit.domain.Review;
import com.ssafit.dto.review.*;
import com.ssafit.exception.ExerciseException;
import com.ssafit.exception.MemberException;
import com.ssafit.exception.ReviewException;
import com.ssafit.exception.status.ExerciseStatus;
import com.ssafit.exception.status.MemberStatus;
import com.ssafit.exception.status.ReviewStatus;
import com.ssafit.repository.ExerciseRepository;
import com.ssafit.repository.MemberRepository;
import com.ssafit.repository.ReviewRepository;
import com.ssafit.vo.review.ReviewWithProfileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public ReviewRegisterResponseDto registerReview(final String studentId, final ReviewRegisterRequestDto requestDto) {
        final Member member = memberRepository.findByStudentId(studentId).orElseThrow(
                () -> new MemberException(MemberStatus.NOT_EXISTING_MEMBER)
        );

        final Exercise exercise = exerciseRepository.findByUuid(requestDto.getExerciseUuid()).orElseThrow(
                () -> new ExerciseException(ExerciseStatus.NOT_EXISTING_EXERCISE)
        );

        Review review = Review.builder()
                .content(requestDto.getContent())
                .exercise(exercise)
                .member(member)
                .build();

        Review result = reviewRepository.save(review);

        return ReviewRegisterResponseDto.builder()
                .uuid(result.getUuid()).build();
    }

    public ReviewReadResponseDto readReview(final String uuid) {
        final Review review = reviewRepository.findByUuid(uuid).orElseThrow(
                () -> new ReviewException(ReviewStatus.NOT_EXISTING_REVIEW)
        );

        updateViewCnt(uuid);

        return ReviewReadResponseDto.builder()
                .uuid(review.getUuid())
                .nickname(review.getMember().getNickname())
                .title(review.getTitle())
                .content(review.getContent())
                .viewCnt(review.getViewCnt())
                .createdDate(review.getCreatedDate())
                .build();
    }

    public ReviewListReadResponseDto readReviewList(final String exerciseUuid, final Pageable pageable) {
        if (exerciseRepository.findByUuid(exerciseUuid).isEmpty()) {
            throw new ExerciseException(ExerciseStatus.NOT_EXISTING_EXERCISE);
        }

        final Page<Review> reviewPage = reviewRepository.findByExerciseUuid(exerciseUuid, pageable);

        final List<Review> reviewContent = reviewPage.getContent();

        final int totalPages = reviewPage.getTotalPages();

        final List<ReviewWithProfileVo> reviews = new ArrayList<>();

        for (final Review review : reviewContent) {
            final ReviewWithProfileVo commentWithProfileVo = ReviewWithProfileVo.builder()
                    .uuid(review.getUuid())
                    .nickname(review.getMember().getNickname())
                    .title(review.getTitle())
                    .viewCnt(review.getViewCnt())
                    .createdDate(review.getCreatedDate())
                    .build();

            reviews.add(commentWithProfileVo);
        }

        return ReviewListReadResponseDto.builder()
                .reviews(reviews)
                .totalPages(totalPages)
                .build();
    }

    @Transactional
    protected void updateViewCnt(final String uuid) {
        final Review review = reviewRepository.findByUuid(uuid).orElseThrow(
                () -> new ReviewException(ReviewStatus.NOT_EXISTING_REVIEW)
        );

        review.updateViewCnt();
    }

    public ReviewUpdateResponseDto updateReview(final String studentId, final ReviewUpdateRequestDto requestDto) {
        final Review review = reviewRepository.findByUuid(requestDto.getUuid()).orElseThrow(
                () -> new ReviewException(ReviewStatus.NOT_EXISTING_REVIEW)
        );

        if (!review.getMember().getStudentId().equals(studentId)) {
            throw new ReviewException(ReviewStatus.UNAUTHORIZED_MEMBER);
        }

        final String title = requestDto.getTitle();
        final String content = requestDto.getContent();

        review.update(title, content);

        return ReviewUpdateResponseDto.builder()
                .title(title)
                .content(content)
                .build();
    }

    public ReviewDeleteResponseDto deleteReview(final String studentId, final String uuid) {
        final Review review = reviewRepository.findByUuid(uuid).orElseThrow(
                () -> new ReviewException(ReviewStatus.NOT_EXISTING_REVIEW)
        );

        if (!review.getMember().getStudentId().equals(studentId)) {
            throw new ReviewException(ReviewStatus.UNAUTHORIZED_MEMBER);
        }

        reviewRepository.delete(review);

        return ReviewDeleteResponseDto.builder()
                .uuid(review.getUuid())
                .build();
    }
}
