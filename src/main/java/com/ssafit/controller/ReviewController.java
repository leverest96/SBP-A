package com.ssafit.controller;

import com.ssafit.dto.review.*;
import com.ssafit.security.userdetails.MemberDetails;
import com.ssafit.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<ReviewRegisterResponseDto> registerReview(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody final ReviewRegisterRequestDto requestDto) {
        final String studentId = memberDetails.getStudentId();

        final ReviewRegisterResponseDto result = reviewService.registerReview(studentId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/read/{uuid}")
    public ResponseEntity<ReviewReadResponseDto> readReview(@PathVariable final String uuid) {
        final ReviewReadResponseDto result = reviewService.readReview(uuid);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/readAll/{uuid}")
    public ResponseEntity<ReviewListReadResponseDto> readReviewList(@PathVariable final String uuid,
                                                                    @RequestParam final int page,
                                                                    @RequestParam final int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        final ReviewListReadResponseDto result = reviewService.readReviewList(uuid, pageable);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{reviewUuid}")
    public ResponseEntity<ReviewUpdateResponseDto> updateReview(@PathVariable final String reviewUuid,
                                                                @AuthenticationPrincipal final MemberDetails memberDetails,
                                                                @RequestBody final ReviewUpdateRequestDto requestDto) {
        final String studentId = memberDetails.getStudentId();

        final ReviewUpdateResponseDto result = reviewService.updateReview(studentId, reviewUuid, requestDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ReviewDeleteResponseDto> deleteReview(@AuthenticationPrincipal final MemberDetails memberDetails,
                                                                  @PathVariable final String uuid) {
        final String studentId = memberDetails.getStudentId();

        final ReviewDeleteResponseDto result = reviewService.deleteReview(studentId, uuid);

        return ResponseEntity.ok(result);
    }
}
