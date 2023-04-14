package com.ssafit.controller;

import com.ssafit.dto.Member.MemberLoginRequestDto;
import com.ssafit.dto.Member.MemberLoginResponseDto;
import com.ssafit.dto.Member.MemberRegisterRequestDto;
import com.ssafit.dto.Member.MemberRegisterResponseDto;
import com.ssafit.properties.jwt.AccessTokenProperties;
import com.ssafit.properties.jwt.RefreshTokenProperties;
import com.ssafit.service.MemberService;
import com.ssafit.utility.common.CookieUtility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @RequestMapping(value = "/email/{email}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkEmailDuplication(@PathVariable final String email) {
        return (memberService.checkEmailDuplication(email)) ?
                (ResponseEntity.ok().build()) :
                (ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/nickname/{nickname}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkNicknameDuplication(@PathVariable final String nickname) {
        return (memberService.checkNicknameDuplication(nickname)) ?
                (ResponseEntity.ok().build()) :
                (ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<MemberRegisterResponseDto> register(@RequestBody final MemberRegisterRequestDto requestDto) {
        final MemberRegisterResponseDto result = memberService.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseDto> login(@RequestBody final MemberLoginRequestDto requestDto,
                                                        final HttpServletResponse response) {
        final MemberLoginResponseDto result = memberService.login(requestDto);

        CookieUtility.addCookie(response, AccessTokenProperties.COOKIE_NAME, result.getAccessToken());
        CookieUtility.addCookie(response, RefreshTokenProperties.COOKIE_NAME, result.getRefreshToken(), 6480000);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(final HttpServletResponse response) {

        CookieUtility.deleteCookie(response, AccessTokenProperties.COOKIE_NAME);
        CookieUtility.deleteCookie(response, RefreshTokenProperties.COOKIE_NAME);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/mailSender")
    public String verificationSender(@RequestParam final String email) throws MessagingException {
        return memberService.verificationSender(email);
    }

    @PostMapping("/verify")
    public void verify(@RequestParam final String code) {
        memberService.verification(code);
    }
}
