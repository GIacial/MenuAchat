package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;

public class RemoveSupplementsModule implements QuestionPopupModule {


    private  Activity activity;
    private TextView text_supplments;
    private TextView text_total;

    public RemoveSupplementsModule(Activity activity,TextView text_supplments , TextView text_total){
        this.text_supplments = text_supplments;
        this.text_total = text_total;
        this.activity = activity;
    }


    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionSuppressionSupplement)  ;
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                NeedingList.getNeeding().resetSupplements();
                text_supplments.setText(""+ NeedingList.getNeeding().getSupplements());
                text_total.setText(""+ NeedingList.getNeeding().getTotal());
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
