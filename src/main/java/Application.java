import controller.MemberController;
import model.service.MemberService;
import view.input.MemberInputView;
import view.output.MemberOutputView;

public class Application {

    public static void main(String[] args) {

        // 객체 조립 진행
        // 추후 배울 Spring 에서는 Application 이 실행되면
        // 모든 객체가 조립이 된다.
        // 지금은 그 구조를 미리 맛보기 형태로 작성해본다.

        MemberService service = new MemberService();
        MemberController controller = new MemberController(service);
        MemberOutputView outputView = new MemberOutputView();
        MemberInputView inputView = new MemberInputView(controller, outputView);

        inputView.displayMainView();

    }

}

