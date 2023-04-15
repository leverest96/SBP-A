package com.ssafit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Review extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "char(36)", nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int viewCnt;

    @JoinColumn(name = "exercise_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Exercise exercise;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @PrePersist
    private void prePersist() {
        createUuid();
    }

    void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void updateViewCnt() {
        this.viewCnt += 1;
    }

    @Builder
    public Review(final Long id, final String title, final String content, final Exercise exercise, final Member member) {
        setExercise(exercise);
        setMember(member);

        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCnt = 0;
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    void setMember(final Member member) {
        if (this.member != null) {
            this.member.getReviews().remove(this);
        }

        this.member = member;

        if (member != null) {
            member.getReviews().add(this);
        }
    }

    void setExercise(final Exercise exercise) {
        if (this.exercise != null) {
            this.exercise.getReviews().remove(this);
        }

        this.exercise = exercise;

        if (exercise != null) {
            exercise.getReviews().add(this);
        }
    }
}
