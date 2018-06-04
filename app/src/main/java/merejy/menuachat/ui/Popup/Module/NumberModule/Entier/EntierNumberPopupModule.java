package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

public interface EntierNumberPopupModule {

    Runnable getMethodOnComfirm(int val);

    Runnable getMethodOnAnuller();

    String getQuestion();
}
