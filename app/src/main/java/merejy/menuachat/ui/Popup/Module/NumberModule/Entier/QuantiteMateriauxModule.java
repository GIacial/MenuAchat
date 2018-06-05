package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.database.Materiaux;
import merejy.menuachat.ui.Activity.Plat.PlatCreator;

public class QuantiteMateriauxModule implements EntierNumberPopupModule {



    private Materiaux ingredient;
    private Activity activity;

    public QuantiteMateriauxModule(Activity activity , Materiaux ingredient){
        this.ingredient = ingredient;
        this.activity = activity;
    }


    @Override
    public Runnable getMethodOnComfirm(final int val) {
        return new Runnable() {
            @Override
            public void run() {


                if(val == 0){
                    Toast.makeText(activity, R.string.error_zeroQuantite,Toast.LENGTH_LONG).show();
                }
                else{
                    Intent secondeActivite = new Intent(activity,PlatCreator.class);

                    PlatCreator.addIngredient(ingredient,val);


                    secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
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
        return activity.getResources().getString(R.string.text_questionQuantite);
    }
}

