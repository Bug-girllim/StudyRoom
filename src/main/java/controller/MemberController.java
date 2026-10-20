package controller;

import model.dto.MemberDTO;
import model.service.MemberService;

import java.util.List;

public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public List<MemberDTO> findAllMembers() {

        List<MemberDTO> memberList = memberService.findAllMembers();

        return memberList;
    }

    // 요청을 Service 로 전달만 한다. 검증은 Service 에서 처리한다.
    public void register(MemberDTO member) {

        memberService.register(member);
    }
}
