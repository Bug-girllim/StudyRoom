package view.input;

import controller.MemberController;
import model.dto.MemberDTO;
import view.output.MemberOutputView;

import java.util.Scanner;

// 회원가입 입력 전용 View : 4개 값을 입력받아 Controller 에 전달한다.
public class MemberRegisterInputView {
    private final MemberController memberController;
    private final MemberOutputView memberOutputView;
    private final Scanner sc;

    // System.in 을 읽는 Scanner 를 여러 개 만들면 입력이 꼬일 수 있어 하나를 공유한다.
    public MemberRegisterInputView(Scanner sc, MemberController memberController, MemberOutputView memberOutputView) {
        this.sc = sc;
        this.memberController = memberController;
        this.memberOutputView = memberOutputView;
    }

    public void register() {

        MemberDTO member = new MemberDTO();

        System.out.print("아이디 : ");
        member.setLoginId(sc.nextLine().trim());
        System.out.print("비밀번호 : ");
        member.setPassword(sc.nextLine().trim());
        System.out.print("이름 : ");
        member.setMemberName(sc.nextLine().trim());
        System.out.print("전화번호 (010-0000-0000) : ");
        member.setPhone(sc.nextLine().trim());

        try {
            memberController.register(member);
            memberOutputView.printMessage("회원가입이 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            memberOutputView.printMessage("회원가입 실패 : " + e.getMessage());
        }
    }
}
