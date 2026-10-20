package view.output;

import model.dto.MemberDTO;

import java.util.List;

public class MemberOutputView {
    public void printMembers(List<MemberDTO> memberList) {

        System.out.println("회원 전체조회 리스트!!");
        for(MemberDTO member : memberList) {
            System.out.println(member);
        }

    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
