package com.example.ITBC.Logger.Security;

import com.example.ITBC.Logger.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long VALID_FOR_SECONDS = 300L;

    private final JwtEncoder encoder;

    @Value("http://localhost:8080")
    private String issuer;

    public String create(User user, List<String> roles) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(VALID_FOR_SECONDS))
                .subject(user.getUsername())
                .claim("roles", roles)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
