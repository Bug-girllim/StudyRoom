import controller.MemberController;
import controller.TimeSlotController;
import model.service.MemberService;
import model.service.TimeSlotService;
import view.input.MemberInputView;
import view.output.MemberOutputView;
import view.input.TimeSlotInputView;
import view.output.TimeSlotOutputView;

public class Application {

    public static void main(String[] args) {

        MemberService memberService = new MemberService();
        MemberController memberController = new MemberController(memberService);
        MemberOutputView memberOutputView = new MemberOutputView();
        MemberInputView memberInputView = new MemberInputView(memberController, memberOutputView);

        TimeSlotService timeSlotService = new TimeSlotService();
        TimeSlotController timeSlotController = new TimeSlotController(timeSlotService);
        TimeSlotOutputView timeSlotOutputView = new TimeSlotOutputView();
        TimeSlotInputView timeSlotInputView = new TimeSlotInputView(timeSlotController, timeSlotOutputView);

        memberInputView.displayMainView();

        timeSlotInputView.showMenu();

    }

}

