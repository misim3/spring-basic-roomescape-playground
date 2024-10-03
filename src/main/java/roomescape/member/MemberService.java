package roomescape.member;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.auth.MemberAuthContext;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse createMember(String name, String email, String password) {
        Member member = memberDao.save(
            new Member(
                name,
                email,
                password,
                "USER"
            )
        );
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    public MemberAuthContext loginByEmailAndPassword(String email, String password) {
        try {
            Member member = memberDao.findByEmailAndPassword(email, password);
            return new MemberAuthContext(member.getName(), member.getEmail());
        } catch (DataAccessException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
