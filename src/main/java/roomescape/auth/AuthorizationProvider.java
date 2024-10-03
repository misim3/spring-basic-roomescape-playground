package roomescape.auth;

public interface AuthorizationProvider {

    MemberCredential create(MemberAuthContext context);

    MemberAuthContext parseCredential(MemberCredential token);
}
