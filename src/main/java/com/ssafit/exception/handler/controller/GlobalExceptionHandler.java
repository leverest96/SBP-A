package com.ssafit.exception.handler.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.ssafit.exception.ExerciseException;
import com.ssafit.exception.MemberException;
import com.ssafit.exception.VerificationException;
import com.ssafit.exception.status.ExerciseStatus;
import com.ssafit.exception.status.MemberStatus;
import com.ssafit.exception.status.VerificationStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        final List<String> messages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.warn("Method argument not valid exception occurrence: {}", messages);

        return ResponseEntity.badRequest().body(new ExceptionResponse(messages));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex) {
        final List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        log.warn("Constraint violation exception occurrence: {}", messages);

        return ResponseEntity.badRequest().body(new ExceptionResponse(messages));
    }

    @ExceptionHandler({JWTCreationException.class})
    public ResponseEntity<Object> handleJWTCreationException(final JWTCreationException ex) {
        final String message = ex.getMessage();

        log.warn("JWT creation exception occurrence: {}", message);

        return ResponseEntity.internalServerError().body(new ExceptionResponse(List.of(message)));
    }

    @ExceptionHandler({MemberException.class})
    public ResponseEntity<Object> handleMemberException(final MemberException ex) {
        final MemberStatus status = ex.getStatus();

        log.warn("Member exception occurrence: {}", status.getMessage());

        return ResponseEntity.status(status.getHttpStatus()).body(new ExceptionResponse(List.of(status.getMessage())));
    }

    @ExceptionHandler({ExerciseException.class})
    public ResponseEntity<Object> handleMemberException(final ExerciseException ex) {
        final ExerciseStatus status = ex.getStatus();

        log.warn("Member exception occurrence: {}", status.getMessage());

        return ResponseEntity.status(status.getHttpStatus()).body(new ExceptionResponse(List.of(status.getMessage())));
    }

    @ExceptionHandler({VerificationException.class})
    public ResponseEntity<Object> handleMemberException(final VerificationException ex) {
        final VerificationStatus status = ex.getStatus();

        log.warn("Member exception occurrence: {}", status.getMessage());

        return ResponseEntity.status(status.getHttpStatus()).body(new ExceptionResponse(List.of(status.getMessage())));
    }

    @RequiredArgsConstructor
    @Getter
    public static class ExceptionResponse {

        private final List<String> messages;

    }
}