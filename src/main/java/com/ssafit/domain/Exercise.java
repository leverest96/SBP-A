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
@Builder
public class Exercise extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @OneToMany(mappedBy = "exercise", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        createUuid();
    }

    void createUuid() {
        this.uuid = UUID.randomUUID().toString();
    }
}
