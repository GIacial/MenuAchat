package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.content.Intent;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Activity.ToActivity;

public class ClearListQuestionModule implements QuestionPopupModule {

    private AbstractActivity activity;

    public  ClearListQuestionModule(AbstractActivity activity){
        this.activity = activity;
    }

    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_ClearList)+" ?";
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                NeedingList.getNeeding().clear();
                Intent home = ToActivity.getIntentToGoTo(activity,activity.getActivityEnum());
                if( home != null){
                    activity.startActivity(home);
                }
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
