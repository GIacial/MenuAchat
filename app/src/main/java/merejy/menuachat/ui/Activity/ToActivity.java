package merejy.menuachat.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public enum ToActivity {
    LIST_INGREDIENT ,
    PLAT_CREATOR ,
    ALL_INGREDIENT,
    ALL_PLAT ,
    COLOR_CONFIGURATOR ,
    INGREDIENT_CREATOR ,
    LIST_PLAT ,
    MAGASIN_CHOICE ,
    MAGASIN_CREATOR;

    static public Intent getIntentToGoTo (Activity activity ,ToActivity toActivity){
        Intent secondActivity = null;
        switch (toActivity){
            case ALL_PLAT:
                secondActivity = new Intent(activity, AllPlatList.class);
                break;
            case LIST_PLAT:
                secondActivity = new Intent(activity, ListePlatActivity.class);
                break;
            case PLAT_CREATOR:
                secondActivity = new Intent(activity, PlatCreator.class);
                break;
            case ALL_INGREDIENT:
                secondActivity = new Intent(activity, All_IngredientList.class);
                break;
            case MAGASIN_CHOICE:
                secondActivity = new Intent(activity, MagasinChoiceActivity.class);
                break;
            case LIST_INGREDIENT:
                secondActivity = new Intent(activity, ListeIngredientActivity.class);
                break;
            case MAGASIN_CREATOR:
                secondActivity = new Intent(activity, MagasinCreator.class);
                break;
            case INGREDIENT_CREATOR:
                secondActivity = new Intent(activity, IngredientCreator.class);
                break;
            case COLOR_CONFIGURATOR:
                secondActivity = new Intent(activity, ColorConfigurationactivity.class);
                break;
            default:
                Toast.makeText(activity,"L'activité de retour est incorrect",Toast.LENGTH_SHORT).show();
                break;
        }
        if(secondActivity != null){
            secondActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        return  secondActivity;
    }
}
