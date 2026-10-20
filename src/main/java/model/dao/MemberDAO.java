package model.dao;


import common.DBUtil;
import model.dto.MemberDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private final Connection connection;

    public MemberDAO(Connection connection) {
        this.connection = connection;
    }

    public List<MemberDTO> findAll() throws SQLException {

        String query = DBUtil.getQuery("member.findAll");
        // 조회 결과를 담을 객체 생성
        List<MemberDTO> members = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                MemberDTO member = new MemberDTO(
                        rset.getInt("MEMBER_ID"),
                        rset.getString("LOGIN_ID"),
                        rset.getString("PASSWORD"),
                        rset.getString("MEMBER_NAME"),
                        rset.getString("PHONE"),
                        rset.getString("MEMBER_ROLE")
                );

                members.add(member);
            }
        }

        return members;
    }

    // 로그인 아이디 중복 여부 확인
    public boolean existsByLoginId(String loginId) throws SQLException {

        String query = DBUtil.getQuery("member.existsByLoginId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, loginId);

            try (ResultSet rset = pstmt.executeQuery()) {
                rset.next();
                return rset.getInt(1) > 0;
            }
        }
    }

    // 회원 등록 (권한은 쿼리에서 USER 로 고정)
    public int insert(MemberDTO member) throws SQLException {

        String query = DBUtil.getQuery("member.insert");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, member.getLoginId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getMemberName());
            pstmt.setString(4, member.getPhone());

            return pstmt.executeUpdate();
        }
    }
}
