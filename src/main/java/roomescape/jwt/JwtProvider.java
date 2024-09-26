package roomescape.jwt;

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

    private static final String USER_IDX = "email";

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
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);
        String tokenValue = Jwts.builder()
            .claim(USER_IDX, payload)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret)))
            .compact();

        return new MemberAuthorization(tokenValue);
    }

    public String parseAuthorization(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret)))
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get(USER_IDX, String.class);
    }
}
