package roomescape.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import roomescape.auth.MemberAuthorization;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class JwtProviderTest {

    private final JwtProvider jwtProvider;

    JwtProviderTest(@Autowired JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Test
    void create_token() {
        String payload = "test";

        MemberAuthorization memberAuthorization = jwtProvider.createByPayload(payload);

        assertNotNull(memberAuthorization.authorization());
    }

    @Test
    void parse_token() {
        String payload = "test";
        MemberAuthorization token = jwtProvider.createByPayload(payload);

        String actual = jwtProvider.parseAuthorization(token.authorization());

        assertEquals(payload, actual);
    }
}