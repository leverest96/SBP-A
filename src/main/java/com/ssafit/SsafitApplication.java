package com.ssafit;

import com.ssafit.properties.GmailProperties;
import com.ssafit.properties.jwt.AccessTokenProperties;
import com.ssafit.properties.jwt.RefreshTokenProperties;
import com.ssafit.properties.security.SecurityCorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({
        SecurityCorsProperties.class,
        AccessTokenProperties.class,
        RefreshTokenProperties.class,
        GmailProperties.class
})
@EnableJpaAuditing
public class SsafitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsafitApplication.class, args);
    }

}
