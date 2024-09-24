package roomescape.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class JwtProviderTest {

    private final JwtProvider jwtProvider;

    JwtProviderTest(@Autowired JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Test
    void create_token() {
        String payload = "test";

        JwtTokenInfo jwtTokenInfo = jwtProvider.createToken(payload);

        assertNotNull(jwtTokenInfo.accessToken());
    }
}