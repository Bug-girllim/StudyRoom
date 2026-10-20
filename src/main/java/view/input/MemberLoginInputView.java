package view.input;

import common.LoginSession;
import controller.MemberController;
import view.output.MemberOutputView;

import java.util.Scanner;

// 로그인 입력 전용 View : 아이디와 비밀번호를 입력받아 Controller 에 전달한다.
public class MemberLoginInputView {
    private final MemberController memberController;
    private final MemberOutputView memberOutputView;
    private final Scanner sc;

    // System.in 을 읽는 Scanner 를 여러 개 만들면 입력이 꼬일 수 있어 하나를 공유한다.
    public MemberLoginInputView(Scanner sc, MemberController memberController, MemberOutputView memberOutputView) {
        this.sc = sc;
        this.memberController = memberController;
        this.memberOutputView = memberOutputView;
    }

    public void login() {

        System.out.print("아이디 : ");
        String loginId = sc.nextLine().trim();
        System.out.print("비밀번호 : ");
        String password = sc.nextLine().trim();

        try {
            memberController.login(loginId, password);
            memberOutputView.printMessage(LoginSession.getLoginMember().getMemberName() + "님 로그인되었습니다.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            memberOutputView.printMessage("로그인 실패 : " + e.getMessage());
        }
    }

    public void logout() {

        try {
            memberController.logout();
            memberOutputView.printMessage("로그아웃되었습니다.");
        } catch (IllegalStateException e) {
            memberOutputView.printMessage("로그아웃 실패 : " + e.getMessage());
        }
    }
}
