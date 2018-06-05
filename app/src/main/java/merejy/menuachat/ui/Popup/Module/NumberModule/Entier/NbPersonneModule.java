package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

import android.content.Intent;

import merejy.menuachat.Exception.NoIngredientAvalilable;
import merejy.menuachat.R;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingPlat.NeedingPlat;
import merejy.menuachat.ui.Activity.Abstract.AbstractActivity;
import merejy.menuachat.ui.Activity.ToActivity;

public class NbPersonneModule implements EntierNumberPopupModule {

    private  AbstractActivity activity;
    private  Plat plat;


    public NbPersonneModule (AbstractActivity activity , Plat plat){
        this.plat = plat;
        this.activity = activity;
    }


    @Override
    public Runnable getMethodOnComfirm(final int val) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    NeedingList.getNeeding().add(new NeedingPlat(plat,val));
                } catch (NoIngredientAvalilable noIngredientAvalilable) {
                    noIngredientAvalilable.printStackTrace();
                }
                Intent secondeActivite = ToActivity.getIntentToGoTo(activity,ToActivity.LIST_PLAT);
                if(secondeActivite != null){
                    activity.startActivity(secondeActivite);
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
        return activity.getResources().getString(R.string.text_questionNbPersonne);
    }
}
