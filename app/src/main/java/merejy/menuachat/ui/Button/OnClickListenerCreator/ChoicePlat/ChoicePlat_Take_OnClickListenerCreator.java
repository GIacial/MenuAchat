package merejy.menuachat.ui.Button.OnClickListenerCreator.ChoicePlat;

import android.view.View;

import merejy.menuachat.database.Plat;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;
import merejy.menuachat.ui.Popup.ChoixAccompagnementPopup;
import merejy.menuachat.ui.Popup.Module.QuestionModule.SelectedPlatQuestionModule;
import merejy.menuachat.ui.Popup.QuestionPopup;

public class ChoicePlat_Take_OnClickListenerCreator implements OnClickListenerCreator<Plat> {

    @Override
    public View.OnClickListener createListener(final Plat item, final AbstractActivity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isAccompagner()){
                    ChoixAccompagnementPopup.showDialog(activity,item);
                }
                else{
                    QuestionPopup.showDialog(activity,new SelectedPlatQuestionModule(item,activity));
                }
            }
        };
    }
}
