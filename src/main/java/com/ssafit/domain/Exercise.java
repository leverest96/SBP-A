package com.ssafit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class Exercise extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "char(36)", nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String fitPartName;

    @Column(nullable = false)
    private String youtubeId;

    @Column(nullable = false)
    private String channelName;

    @Column(nullable = false)
    private int viewCnt;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Exercise(final Long id,
                    final String uuid,
                    final String url,
                    final String title,
                    final String fitPartName,
                    final String youtubeId,
                    final String channelName,
                    final Member member,
                    final List<Review> reviews) {
        setMember(member);

        this.id = id;
        this.uuid = uuid;
        this.url = url;
        this.title = title;
        this.fitPartName = fitPartName;
        this.youtubeId = youtubeId;
        this.channelName = channelName;
        this.viewCnt = 0;
        this.reviews = (reviews == null) ? (new ArrayList<>()) : (reviews);
    }

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

    void setMember(final Member member) {
        if (this.member != null) {
            this.member.getExercises().remove(this);
        }

        this.member = member;

        if (member != null) {
            member.getExercises().add(this);
        }
    }
}
