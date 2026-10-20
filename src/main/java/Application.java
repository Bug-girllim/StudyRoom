import controller.MemberController;
import model.service.MemberService;
import view.input.MemberInputView;
import view.output.MemberOutputView;
import view.input.TimeSlotInputView;

public class Application {

    public static void main(String[] args) {
      
        MemberService service = new MemberService();
        MemberController controller = new MemberController(service);
        MemberOutputView outputView = new MemberOutputView();
        MemberInputView inputView = new MemberInputView(controller, outputView);

        inputView.displayMainView();
      
        TimeSlotInputView timeSlotView = new TimeSlotInputView();

        timeSlotView.showMenu();
  

    }

}

