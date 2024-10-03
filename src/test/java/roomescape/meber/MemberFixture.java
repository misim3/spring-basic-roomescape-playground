package roomescape.meber;

import roomescape.member.Member;

public class MemberFixture {

    public static Member memberWithName(String name) {
        return new Member(name, "email", "password", "USER");
    }
}
