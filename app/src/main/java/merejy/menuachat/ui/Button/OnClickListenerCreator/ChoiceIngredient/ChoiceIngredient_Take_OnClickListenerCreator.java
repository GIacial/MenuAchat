package merejy.menuachat.ui.Button.OnClickListenerCreator.ChoiceIngredient;


import android.app.Activity;
import android.view.View;

import merejy.menuachat.database.Ingredient;
import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.QuantiteIngredientModule;

public class ChoiceIngredient_Take_OnClickListenerCreator implements OnClickListenerCreator<Ingredient> {
    @Override
    public View.OnClickListener createListener(final Ingredient item, final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntierNumberPopUp.showDialog(activity,new QuantiteIngredientModule(activity, All_IngredientList.target,item));

            }
        };
    }
}
