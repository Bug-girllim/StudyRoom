package model.service;

import common.LoginSession;
import common.config.JDBCTemplate;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberService {

    private static final String PHONE_PATTERN = "^01\\d-\\d{3,4}-\\d{4}$";

    public List<MemberDTO> findAllMembers() {

        // try-with-resource 절
        // try-catch 절은 사용한 자원을 finally 구문에서 수동 해제해야 한다.
        // try() 소괄호 내부 자원을 자동 해제 해준다.
        try (Connection con = JDBCTemplate.getConnection()) {

            return new MemberDAO(con).findAll();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 회원가입 : 입력값 검증 -> 아이디 중복 확인 -> 등록
    public void register(MemberDTO member) {

        if (isBlank(member.getLoginId())) {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }
        if (isBlank(member.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
        if (isBlank(member.getMemberName())) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
        if (member.getPhone() == null || !member.getPhone().matches(PHONE_PATTERN)) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
        }

        try (Connection con = JDBCTemplate.getConnection()) {

            MemberDAO memberDAO = new MemberDAO(con);

            if (memberDAO.existsByLoginId(member.getLoginId())) {
                throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
            }

            memberDAO.insert(member);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인 : 이미 로그인 여부 확인 -> 입력값 검증 -> 회원 조회 -> 비밀번호 비교 -> 세션 저장
    public void login(String loginId, String password) {

        if (LoginSession.isLoggedIn()) {
            throw new IllegalStateException("이미 로그인 상태입니다.");
        }
        if (isBlank(loginId)) {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }
        if (isBlank(password)) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        try (Connection con = JDBCTemplate.getConnection()) {

            MemberDTO member = new MemberDAO(con).findByLoginId(loginId);

            // 아이디 존재 여부를 알려주지 않도록 같은 메시지를 사용한다.
            if (member == null || !member.getPassword().equals(password)) {
                throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
            }

            // 세션에는 비밀번호를 보관하지 않는다.
            member.setPassword(null);
            LoginSession.login(member);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그아웃 : 로그인 상태가 아니면 거부
    public void logout() {

        if (!LoginSession.isLoggedIn()) {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }

        LoginSession.logout();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
