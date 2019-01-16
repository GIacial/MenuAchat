package merejy.menuachat.ui.Popup.Module.QuestionModule;

public class PrintTextQuestionModule implements QuestionPopupModule {

    private String text ;

    public PrintTextQuestionModule (String text){
        this.text = text;
    }


    @Override
    public String getQuestion() {
        return text;
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return null;
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
