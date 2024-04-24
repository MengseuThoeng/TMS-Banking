package com.seu.tms_mobile_banking.features.token;

import com.seu.tms_mobile_banking.features.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService{

    private final String TOKEN_TYPE = "Bearer";
    private final JwtEncoder jwtEncoder;

    private JwtEncoder refreshJwtEncoder;

    @Qualifier("refreshJwtEncoder")
    @Autowired
    public void setRefreshJwtEncoder(JwtEncoder refreshJwtEncoder) {
        this.refreshJwtEncoder = refreshJwtEncoder;
    }

    @Override
    public AuthResponse createToken(Authentication auth) {
        return new AuthResponse(
                TOKEN_TYPE,
                accessToken(auth),
                refreshToken(auth)
        );
    }

    @Override
    public String accessToken(Authentication auth) {
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                //.filter(authority -> !authority.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.MINUTES))
                .issuer(auth.getName())
                .claim("scope",scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    @Override
    public String refreshToken(Authentication auth) {
        JwtClaimsSet refreshClaimSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access Resource")
                .audience(List.of("WEB","MOBILE"))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .issuer(auth.getName())
                .build();
        return refreshJwtEncoder.encode(JwtEncoderParameters.from(refreshClaimSet)).getTokenValue();
    }
}
