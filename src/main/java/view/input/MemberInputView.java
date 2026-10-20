package view.input;

import common.LoginSession;
import controller.MemberController;
import model.dto.MemberDTO;
import view.output.MemberOutputView;

import java.util.List;
import java.util.Scanner;

public class MemberInputView {
    private final MemberController memberController;
    private final MemberOutputView memberOutputView;
    private final MemberRegisterInputView memberRegisterInputView;
    private final MemberLoginInputView memberLoginInputView;
    private final MemberUpdateInputView memberUpdateInputView;
    private final Scanner sc = new Scanner(System.in);

    // 생성자를 통한 필드 초기화
    public MemberInputView(MemberController memberController, MemberOutputView memberOutputView) {
        this.memberController = memberController;
        this.memberOutputView = memberOutputView;
        this.memberRegisterInputView = new MemberRegisterInputView(sc, memberController, memberOutputView);
        this.memberLoginInputView = new MemberLoginInputView(sc, memberController, memberOutputView);
        this.memberUpdateInputView = new MemberUpdateInputView(sc, memberController, memberOutputView);
    }

    public void displayMainView() {

        while (true) {
            System.out.println("StudyRoom");
            if (LoginSession.isLoggedIn()) {
                System.out.println("[로그인 : " + LoginSession.getLoginMember().getMemberName() + "]");
            }
            System.out.println("1. 회원 전체 조회");
            System.out.println("2. 회원가입");
            System.out.println("3. 로그인");
            System.out.println("4. 로그아웃");
            System.out.println("5. 내 정보 조회");
            System.out.println("6. 내 정보 수정");
            System.out.println("0. 종료");
            System.out.print("메뉴를 입력해주세요 : ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    try {
                        List<MemberDTO> memberList = memberController.findAllMembers();
                        memberOutputView.printMembers(memberList);
                    } catch (IllegalStateException e) {
                        memberOutputView.printMessage("조회 실패 : " + e.getMessage());
                    }
                    break;
                case "2":
                    memberRegisterInputView.register();
                    break;
                case "3":
                    memberLoginInputView.login();
                    break;
                case "4":
                    memberLoginInputView.logout();
                    break;
                case "5":
                    try {
                        memberOutputView.printMyInfo(memberController.findMyInfo());
                    } catch (IllegalStateException e) {
                        memberOutputView.printMessage("조회 실패 : " + e.getMessage());
                    }
                    break;
                case "6":
                    memberUpdateInputView.update();
                    break;
                case "0":
                    memberOutputView.printMessage("프로그램을 종료합니다.");
                    return;
                default:
                    memberOutputView.printMessage("잘못된 입력입니다.");
            }
        }

    }
}
