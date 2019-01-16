package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.content.Intent;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Activity.ToActivity;

public class ModifyDatabaseQuestionModule implements QuestionPopupModule {

    private AbstractActivity activity;
    private ToActivity target;

    public  ModifyDatabaseQuestionModule(AbstractActivity activity,ToActivity target){
        this.activity = activity;
        this.target = target;
    }

    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionDatabaseModifier);
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                NeedingList.getNeeding().clear();
                Intent home = ToActivity.getIntentToGoTo(activity,target);
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
