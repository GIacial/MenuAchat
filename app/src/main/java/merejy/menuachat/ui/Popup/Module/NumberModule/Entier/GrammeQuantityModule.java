package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

import android.content.Intent;

import merejy.menuachat.R;
import merejy.menuachat.database.Materiaux;
import merejy.menuachat.ui.Activity.Abstract.AbstractActivity;
import merejy.menuachat.ui.Activity.Materiaux.AllMateriauxActivity;
import merejy.menuachat.ui.Activity.Plat.PlatCreator;
import merejy.menuachat.ui.Activity.ToActivity;

public class GrammeQuantityModule implements EntierNumberPopupModule {



    private AbstractActivity activity;
    private Materiaux materiaux;

    public GrammeQuantityModule(AbstractActivity activity , Materiaux materiaux){
        this.activity = activity;
        this.materiaux = materiaux;
    }


    @Override
    public Runnable getMethodOnComfirm(final int val) {
        return new Runnable() {
            @Override
            public void run() {
                if( val > 0){
                    PlatCreator.addIngredient(materiaux,val);
                }
                Intent secondActivity = ToActivity.getIntentToGoTo(activity, ToActivity.PLAT_CREATOR);
                if(secondActivity != null){
                    activity.startActivity(secondActivity);
                }
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }

    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionQuantiteGramme);
    }
}
