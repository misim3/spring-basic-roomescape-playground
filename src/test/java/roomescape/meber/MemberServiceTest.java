package roomescape.meber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.auth.MemberAuthContext;
import roomescape.member.Member;
import roomescape.member.MemberService;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void create_member() {
        String memberName = "name";
        Member member = MemberFixture.memberWithName(memberName);

        assertThatCode(() -> memberService.createMember(
            member.getName(),
            member.getEmail(),
            member.getPassword()
            )
        ).doesNotThrowAnyException();
    }

    @Test
    void login_by_email_and_password() {
        Member member = MemberFixture.memberWithName("name");
        memberService.createMember(
            member.getName(),
            member.getEmail(),
            member.getPassword()
        );

        MemberAuthContext loginMember = memberService.loginByEmailAndPassword(member.getEmail(), member.getPassword());

        assertThat(loginMember.name()).isEqualTo(member.getName());
    }

    @Test
    void throw_when_try_to_login_with_invalid_info() {
        String email = "email";
        String password = "password";

        assertThatThrownBy(() -> memberService.loginByEmailAndPassword(email, password))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid email or password");
    }
}
