package roomescape.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.auth.MemberAuthContext;
import roomescape.auth.MemberCredential;
import roomescape.meber.MemberFixture;
import roomescape.member.Member;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JwtProviderTest {

    private final JwtProvider jwtProvider;

    JwtProviderTest(@Autowired JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Test
    void create_token() {
        String memberName = "test";
        Member member = MemberFixture.memberWithName(memberName);
        MemberAuthContext context = new MemberAuthContext(member.getName(), member.getRole());

        MemberCredential token = jwtProvider.create(context);

        assertNotNull(token.authorization());
    }

    @Test
    void parse_token() {
        String memberName = "test";
        Member member = MemberFixture.memberWithName(memberName);
        MemberAuthContext context = new MemberAuthContext(member.getName(), member.getRole());

        MemberCredential token = jwtProvider.create(context);

        MemberAuthContext actual = jwtProvider.parseCredential(token);

        assertEquals(context, actual);
    }
}