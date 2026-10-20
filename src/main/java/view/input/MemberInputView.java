package view.input;

import controller.MemberController;
import model.dto.MemberDTO;
import view.output.MemberOutputView;

import java.util.List;
import java.util.Scanner;

public class MemberInputView {
    private final MemberController memberController;
    private final MemberOutputView memberOutputView;
    private final Scanner sc = new Scanner(System.in);

    // 생성자를 통한 필드 초기화
    public MemberInputView(MemberController memberController, MemberOutputView memberOutputView) {
        this.memberController = memberController;
        this.memberOutputView = memberOutputView;
    }

    public void displayMainView() {

        while (true) {
            System.out.println("StudyRoom");
            System.out.println("1. 회원 전체 조회");
            System.out.println("0. 종료");
            System.out.print("메뉴를 입력해주세요 : ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    List<MemberDTO> memberList = memberController.findAllMembers();
                    memberOutputView.printMembers(memberList);
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
