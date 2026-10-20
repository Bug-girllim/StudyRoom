package common;

import model.dto.MemberDTO;

// 로그인한 회원을 프로그램 전체에서 기억한다. (콘솔 프로그램이라 메모리에만 보관)
public class LoginSession {

    private static MemberDTO loginMember;

    private LoginSession() {
    }

    public static void login(MemberDTO member) {
        loginMember = member;
    }

    public static void logout() {
        loginMember = null;
    }

    public static boolean isLoggedIn() {
        return loginMember != null;
    }

    public static MemberDTO getLoginMember() {
        return loginMember;
    }

    public static boolean isAdmin() {
        return loginMember != null && "ADMIN".equals(loginMember.getMemberRole());
    }
}
