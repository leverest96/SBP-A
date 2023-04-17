package com.ssafit.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.ssafit.domain.Member;
import com.ssafit.domain.Role;
import com.ssafit.domain.Verification;
import com.ssafit.dto.Member.MemberLoginRequestDto;
import com.ssafit.dto.Member.MemberLoginResponseDto;
import com.ssafit.dto.Member.MemberRegisterRequestDto;
import com.ssafit.dto.Member.MemberRegisterResponseDto;
import com.ssafit.exception.MemberException;
import com.ssafit.exception.VerificationException;
import com.ssafit.exception.status.MemberStatus;
import com.ssafit.exception.status.VerificationStatus;
import com.ssafit.repository.MemberRepository;
import com.ssafit.repository.VerificationRepository;
import com.ssafit.utility.jwt.JwtProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final VerificationRepository verificationRepository;

    private final JwtProvider accessTokenProvider;
    private final JwtProvider refreshTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;

    public boolean checkEmailDuplication(final String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean checkNicknameDuplication(final String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    public boolean checkVerification(final String email) {
        final Verification verification = verificationRepository.findByEmail(email).orElseThrow(
                () -> new VerificationException(VerificationStatus.NOT_EXISTING_EMAIL)
        );

        return verification.isState();
    }

    @Transactional
    public MemberRegisterResponseDto register(final MemberRegisterRequestDto requestDto) {
        if (checkEmailDuplication(requestDto.getEmail())) {
            throw new MemberException(MemberStatus.EXISTING_EMAIL);
        }

        if (checkNicknameDuplication(requestDto.getNickname())) {
            throw new MemberException(MemberStatus.NOT_EXISTING_NICKNAME);
        }

        if (!checkVerification(requestDto.getEmail())) {
            throw new VerificationException(VerificationStatus.UNVERIFIED);
        }

        final Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .studentId(requestDto.getStudentId())
                .phone(requestDto.getPhone())
                .image("imageInput")
                .role((requestDto.isAdmin()) ? (Role.ADMIN) : (Role.USER))
                .build();

        final Member result = memberRepository.save(member);

        return MemberRegisterResponseDto.builder()
                .nickname(result.getNickname())
                .build();
    }

    public MemberLoginResponseDto login(final MemberLoginRequestDto requestDto) throws JWTCreationException {
        final Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new MemberException(MemberStatus.NOT_EXISTING_EMAIL)
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new MemberException(MemberStatus.INCORRECT_PASSWORD);
        }

        final Map<String, String> payload = new HashMap<>();

        payload.put("studentId", String.valueOf(member.getStudentId()));
        payload.put("nickname", member.getNickname());
        payload.put("role", member.getRole().getDisplayName());

        final Map<String, String> refreshPayload = new HashMap<>();

        payload.put("studentId", member.getStudentId());

        return MemberLoginResponseDto.builder()
                .accessToken(accessTokenProvider.generate(payload))
                .refreshToken(refreshTokenProvider.generate(refreshPayload))
                .build();
    }

    @Transactional
    public String verificationSender(String email) throws MessagingException {
        if (checkEmailDuplication(email)) {
            throw new MemberException(MemberStatus.EXISTING_EMAIL);
        }

        final Verification verification = verificationRepository.findByEmail(email)
                .orElseGet(() -> createBasicVerification(email));

        verification.unverify();
        verification.refreshCode();

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("SSAFIT verification code");
        helper.setText("This is your verification code: " + verification.getCode());

        mailSender.send(message);

        return verification.getCode();
    }

    @Transactional
    public Verification createBasicVerification(final String email) {
        final Verification verification = Verification.builder()
                .email(email)
                .build();
        return verificationRepository.save(verification);
    }

    @Transactional
    public void verification(final String code) {
        final Verification verification = verificationRepository.findByCode(code)
                .orElseThrow(() -> new VerificationException(VerificationStatus.NOT_EXISTING_CODE));

        if (code.equals(verification.getCode())) {
            verification.verify();
        }
    }
}
