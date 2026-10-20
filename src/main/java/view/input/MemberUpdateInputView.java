package view.input;

import controller.MemberController;
import model.dto.MemberDTO;
import view.output.MemberOutputView;

import java.util.Scanner;

// 내 정보 수정 입력 전용 View : 현재 비밀번호와 변경할 값을 입력받아 Controller 에 전달한다.
public class MemberUpdateInputView {
    private final MemberController memberController;
    private final MemberOutputView memberOutputView;
    private final Scanner sc;

    // System.in 을 읽는 Scanner 를 여러 개 만들면 입력이 꼬일 수 있어 하나를 공유한다.
    public MemberUpdateInputView(Scanner sc, MemberController memberController, MemberOutputView memberOutputView) {
        this.sc = sc;
        this.memberController = memberController;
        this.memberOutputView = memberOutputView;
    }

    public void update() {

        System.out.print("현재 비밀번호 : ");
        String currentPassword = sc.nextLine().trim();

        System.out.println("변경하지 않을 항목은 엔터를 누르세요.");
        MemberDTO changes = new MemberDTO();

        System.out.print("새 비밀번호 : ");
        changes.setPassword(sc.nextLine().trim());
        System.out.print("새 이름 : ");
        changes.setMemberName(sc.nextLine().trim());
        System.out.print("새 전화번호 (010-0000-0000) : ");
        changes.setPhone(sc.nextLine().trim());

        try {
            memberController.updateMyInfo(currentPassword, changes);
            memberOutputView.printMessage("내 정보가 수정되었습니다.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            memberOutputView.printMessage("수정 실패 : " + e.getMessage());
        }
    }
}
