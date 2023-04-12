package com.ssafit.security.userdetails;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ssafit.properties.jwt.AccessTokenProperties.AccessTokenClaim;
import com.ssafit.utility.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@RequiredArgsConstructor
@Slf4j
public class MemberDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final JwtProvider accessTokenProvider;

    @Override
    public UserDetails loadUserDetails(final PreAuthenticatedAuthenticationToken token) throws AuthenticationException {
        try {
            final String accessToken = (String) token.getCredentials();
            final DecodedJWT decodedAccessToken = accessTokenProvider.verify(accessToken);

            final String studentId = decodedAccessToken.getClaim(AccessTokenClaim.STUDENT_ID.getClaim()).asString();
            final String nickname = decodedAccessToken.getClaim(AccessTokenClaim.NICKNAME.getClaim()).asString();
            final String[] role = {decodedAccessToken.getClaim(AccessTokenClaim.ROLE.getClaim()).asString()};

            log.info("Member authentication request: {}", studentId);

            return MemberDetails.builder()
                    .studentId(studentId)
                    .nickname(nickname)
                    .authorities(role)
                    .build();
        } catch (final JWTVerificationException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }
}