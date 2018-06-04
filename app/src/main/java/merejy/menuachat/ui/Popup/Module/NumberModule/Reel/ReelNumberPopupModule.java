package merejy.menuachat.ui.Popup.Module.NumberModule.Reel;

public interface ReelNumberPopupModule {

    Runnable getMethodOnComfirm(double val);

    Runnable getMethodOnAnuller();

    String getQuestion();
}
