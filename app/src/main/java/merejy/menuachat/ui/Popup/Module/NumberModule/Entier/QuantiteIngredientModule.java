package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.ui.Activity.ListeIngredientActivity;
import merejy.menuachat.ui.Activity.PlatCreator;
import merejy.menuachat.ui.Activity.ToActivity;


public class QuantiteIngredientModule implements EntierNumberPopupModule {



     private Ingredient ingredient;
     private ToActivity target;
     private Activity activity;

     public QuantiteIngredientModule(Activity activity , ToActivity target , Ingredient ingredient){
         this.ingredient = ingredient;
         this.activity = activity;
         this.target = target;
     }


    @Override
    public Runnable getMethodOnComfirm(final int val) {
        return new Runnable() {
            @Override
            public void run() {
                NeedingIngredient need = new NeedingIngredient(ingredient);
                need.addQuantite(val-1);

               // Intent secondeActivite = ToActivity.getIntentToGoTo(activity,target) ;
                if(need.getQuantite() == 0){
                    Toast.makeText(activity, R.string.error_zeroQuantite,Toast.LENGTH_LONG).show();
                }
                switch (target){
                    case LIST_INGREDIENT :
                        if(need.getQuantite() != 0){
                            NeedingList.getNeeding().add(need);
                        }
                        break;
                    case PLAT_CREATOR:
                        for(int i = 0 ; i<val ; i++){
                            PlatCreator.addIngredient(ingredient);
                        }
                        break;
                    default:break;
                }

               // if(secondeActivite != null){
                    Toast.makeText(activity,need.getQuantite()+" "+ingredient.getNom()+ activity.getResources().getString(R.string.text_Ajoutcomfirmer) ,Toast.LENGTH_SHORT).show();
                   // activity.startActivity(secondeActivite);
               // }

            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }

    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionQuantite_1)+" "+ingredient.getNom()+" "+activity.getResources().getString(R.string.text_questionQuantite_2);
    }
}
