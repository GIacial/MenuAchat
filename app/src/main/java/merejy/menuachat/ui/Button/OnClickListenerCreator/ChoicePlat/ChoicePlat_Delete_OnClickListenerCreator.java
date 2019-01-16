package merejy.menuachat.ui.Button.OnClickListenerCreator.ChoicePlat;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import merejy.menuachat.database.Plat;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;
import merejy.menuachat.ui.Popup.Module.QuestionModule.DeletePlatQuestionModule;
import merejy.menuachat.ui.Popup.QuestionPopup;

public class ChoicePlat_Delete_OnClickListenerCreator implements OnClickListenerCreator<Plat> {

    private RecyclerView liste;
    public ChoicePlat_Delete_OnClickListenerCreator(RecyclerView liste){
        this.liste = liste;
    }

    @Override
    public View.OnClickListener createListener(final Plat item, final AbstractActivity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPopup.showDialog(activity,new DeletePlatQuestionModule(item,liste,activity));
            }
        };
    }
}
