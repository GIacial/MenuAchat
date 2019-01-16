package merejy.menuachat.ui.Button.OnClickListenerCreator.ChoiceIngredient;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import merejy.menuachat.database.Ingredient;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;
import merejy.menuachat.ui.Popup.Module.QuestionModule.DeleteIngredientQuestionModule;
import merejy.menuachat.ui.Popup.QuestionPopup;

public class ChoiceIngredient_Delete_OnClickListenerCreator implements OnClickListenerCreator<Ingredient> {

    private RecyclerView liste;
    private CheckBox choiceInterface;

    public ChoiceIngredient_Delete_OnClickListenerCreator(RecyclerView liste, CheckBox choiceInterface){
        this.liste = liste;
        this.choiceInterface = choiceInterface;
    }



    @Override
    public View.OnClickListener createListener(final Ingredient item,final AbstractActivity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPopup.showDialog(activity,new DeleteIngredientQuestionModule(item,liste,activity,choiceInterface));
            }
        };
    }
}
