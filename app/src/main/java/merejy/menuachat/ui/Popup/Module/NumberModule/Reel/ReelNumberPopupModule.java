package merejy.menuachat.ui.Popup.Module.NumberModule.Reel;

import android.view.View;

public interface ReelNumberPopupModule {

    Runnable getMethodOnComfirm(double val);

    Runnable getMethodOnAnuller();

    String getQuestion();

    void configLayout(View layout);
}
