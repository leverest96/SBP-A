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

    @GetMapping("/read/one/{uuid}")
    public ResponseEntity<ExerciseReadResponseDto> readExercise(@PathVariable final String uuid) {
        final ExerciseReadResponseDto result = exerciseService.readExercise(uuid);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/read/{fitPartName}")
    public ResponseEntity<ExerciseListReadResponseDto> readExerciseList(@PathVariable final String fitPartName) {
        final ExerciseListReadResponseDto exercises = exerciseService.readExerciseList(fitPartName);

        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/read")
    public ResponseEntity<ExerciseListReadResponseDto> readAllExerciseList() {
        final ExerciseListReadResponseDto exercises = exerciseService.readAllExerciseList();

        return ResponseEntity.ok(exercises);
    }

    @PatchMapping("/cnt/{uuid}")
    public ResponseEntity<Void> updateViewCnt(@PathVariable final String uuid) {
        exerciseService.updateViewCnt(uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ExerciseDeleteResponseDto> deleteExercise(@AuthenticationPrincipal final MemberDetails memberDetails,
                                                               @PathVariable final String uuid) {
        final String studentId = memberDetails.getStudentId();

        final ExerciseDeleteResponseDto result = exerciseService.deleteExercise(studentId, uuid);

        return ResponseEntity.ok(result);
    }
}
