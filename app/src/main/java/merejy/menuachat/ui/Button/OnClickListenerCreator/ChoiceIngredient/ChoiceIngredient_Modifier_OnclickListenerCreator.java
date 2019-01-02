package merejy.menuachat.ui.Button.OnClickListenerCreator.ChoiceIngredient;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import merejy.menuachat.database.Ingredient;
import merejy.menuachat.ui.Activity.IngredientCreator;
import merejy.menuachat.ui.Activity.ToActivity;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;

public class ChoiceIngredient_Modifier_OnclickListenerCreator implements OnClickListenerCreator<Ingredient> {
    @Override
    public View.OnClickListener createListener(final Ingredient item,final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientCreator.setModifyMode(item);
                Intent secondActivity = ToActivity.getIntentToGoTo(activity,ToActivity.INGREDIENT_CREATOR);
                if(secondActivity != null){
                    activity.startActivity(secondActivity);
                }
            }
        };
    }
}
