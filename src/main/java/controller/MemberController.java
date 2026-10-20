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
}
