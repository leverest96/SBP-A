package com.ssafit.vo.exercise;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
@Builder
public class ExerciseVo {
    private final String uuid;

    private final String title;

    private final String url;

    private final String fitPartName;

    private final String youtubeId;

    private final String channelName;
}
