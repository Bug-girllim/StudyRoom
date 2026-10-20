package view.output;

import model.dto.MemberDTO;

import java.util.List;

public class MemberOutputView {
    public void printMembers(List<MemberDTO> memberList) {

        System.out.println("회원 전체조회 리스트!!");
        for(MemberDTO member : memberList) {
            // 비밀번호는 출력하지 않는다.
            System.out.println("회원번호 : " + member.getMemberId()
                    + " | 아이디 : " + member.getLoginId()
                    + " | 이름 : " + member.getMemberName()
                    + " | 전화번호 : " + member.getPhone()
                    + " | 권한 : " + member.getMemberRole());
        }

    }

    public void printMyInfo(MemberDTO member) {

        // 비밀번호는 출력하지 않는다.
        System.out.println("===== 내 정보 =====");
        System.out.println("회원번호 : " + member.getMemberId());
        System.out.println("아이디 : " + member.getLoginId());
        System.out.println("이름 : " + member.getMemberName());
        System.out.println("전화번호 : " + member.getPhone());
        System.out.println("권한 : " + member.getMemberRole());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
