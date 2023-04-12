package com.ssafit.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gmail")
@Getter
@RequiredArgsConstructor
public class GmailProperties {
    private final String username;
    private final String password;
}
