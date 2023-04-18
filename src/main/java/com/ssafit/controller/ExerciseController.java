package com.ssafit.controller;

import com.ssafit.dto.Exercise.*;
import com.ssafit.security.userdetails.MemberDetails;
import com.ssafit.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping("/register")
    public ResponseEntity<ExerciseRegisterResponseDto> registerExercise(@AuthenticationPrincipal final MemberDetails memberDetails,
                                                                        @RequestBody final ExerciseRegisterRequestDto requestDto) {
        final String studentId = memberDetails.getStudentId();

        final ExerciseRegisterResponseDto result = exerciseService.registerExercise(studentId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/read/{uuid}")
    public ResponseEntity<ExerciseReadResponseDto> readExercise(@PathVariable final String uuid) {
        final ExerciseReadResponseDto result = exerciseService.readExercise(uuid);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/read/{fitPartName}")
    public ResponseEntity<ExerciseListReadResponseDto> readExerciseList(@PathVariable final String fitPartName,
                                                                        @RequestParam final int page,
                                                                        @RequestParam final int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("viewCnt").descending());

        final ExerciseListReadResponseDto result = exerciseService.readExerciseList(fitPartName, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/read")
    public ResponseEntity<ExerciseListReadResponseDto> readAllExerciseList(@RequestParam final int page, @RequestParam final int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("viewCnt").descending());

        final ExerciseListReadResponseDto result = exerciseService.readAllExerciseList(pageable);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ExerciseDeleteResponseDto> deleteExercise(@AuthenticationPrincipal final MemberDetails memberDetails,
                                                               @PathVariable final String uuid) {
        final String studentId = memberDetails.getStudentId();

        final ExerciseDeleteResponseDto result = exerciseService.deleteExercise(studentId, uuid);

        return ResponseEntity.ok(result);
    }
}
