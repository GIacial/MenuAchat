package merejy.menuachat.ui.Popup.Module.ColorModule;

public interface ColorPopupModule {

    Runnable getMethodOnComfirm(int red , int green , int blue);

    Runnable getMethodOnAnuller();
}
