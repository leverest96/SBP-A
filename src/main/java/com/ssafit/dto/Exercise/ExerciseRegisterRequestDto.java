package com.ssafit.dto.Exercise;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class ExerciseRegisterRequestDto {
    private final String uuid;
    private final String title;
    private final String url;
    private final String fitPartName;
    private final String youtubeId;
    private final String channelName;
}
