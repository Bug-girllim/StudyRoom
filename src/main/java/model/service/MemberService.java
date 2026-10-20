package model.service;

import common.config.JDBCTemplate;
import model.dao.MemberDAO;
import model.dto.MemberDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberService {

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
}
