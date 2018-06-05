package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

import android.content.Intent;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.ui.Activity.Abstract.AbstractActivity;
import merejy.menuachat.ui.Activity.ToActivity;


public class QuantiteIngredientModule implements EntierNumberPopupModule {



     private Ingredient ingredient;
     private AbstractActivity activity;

     public QuantiteIngredientModule(AbstractActivity activity  , Ingredient ingredient){
         this.ingredient = ingredient;
         this.activity = activity;
     }


    @Override
    public Runnable getMethodOnComfirm(final int val) {
        return new Runnable() {
            @Override
            public void run() {
                NeedingIngredient need = new NeedingIngredient(ingredient);
                need.addQuantite(val-1);


                if(need.getQuantite() == 0){
                    Toast.makeText(activity, R.string.error_zeroQuantite,Toast.LENGTH_LONG).show();
                }
                else{
                    Intent secondeActivite = ToActivity.getIntentToGoTo(activity,ToActivity.LIST_INGREDIENT);
                    NeedingList.getNeeding().add(need);

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
