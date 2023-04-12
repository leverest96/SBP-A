package com.ssafit.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Getter
@Builder
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;

    @Column(nullable = false)
    private final String email;

    @Column
    private String code;

    @Column
    private boolean state;

    public void verify() {
        this.state = true;
    }

    public void unverify() {
        this.state = false;
    }

    public void refreshCode() {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder randomString = new StringBuilder(6);
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        this.code = randomString.toString();
    }
}
