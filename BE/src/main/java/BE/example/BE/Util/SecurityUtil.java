package BE.example.BE.Util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {
    private final JwtEncoder jwtEnCoder;

    public SecurityUtil(JwtEncoder jwtEnCoder) {
        this.jwtEnCoder = jwtEnCoder;
    }

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    @Value("${be.jwt.base64-secret}")
    private String jwtKey;

    @Value("${be.jwt.token-validity-in-seconds}")
    private long jwtExpiration;

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.jwtExpiration, ChronoUnit.SECONDS);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now).expiresAt(validity)
                .subject(authentication.getName())
                .claim("chithanh", authentication)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEnCoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
