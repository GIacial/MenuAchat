package merejy.menuachat.ui.Popup.QuestionModule;

public interface QuestionPopupModule {

     String getQuestion();

     Runnable getMethodOnComfirm();

     Runnable getMethodOnAnuller();
}
