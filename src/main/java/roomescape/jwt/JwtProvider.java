package roomescape.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import roomescape.auth.AuthorizationProvider;
import roomescape.auth.MemberAuthorization;

@Component
public class JwtProvider implements AuthorizationProvider {

    private final String jwtSecret;
    private final Long jwtExpiration;

    public JwtProvider(
        @Value("${roomescape.auth.jwt.secret}") String jwtSecret,
        @Value("${roomescape.auth.jwt.expiration}") Long jwtExpiration
    ) {
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    public MemberAuthorization createByPayload(String payload) {
        Claims claims = Jwts.claims().setSubject(payload);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);
        String tokenValue = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret)))
            .compact();

        return new MemberAuthorization(tokenValue);
    }
}
