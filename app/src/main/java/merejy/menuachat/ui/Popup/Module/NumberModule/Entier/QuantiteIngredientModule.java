package merejy.menuachat.ui.Popup.Module.NumberModule.Entier;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.ui.Activity.ListeIngredientActivity;
import merejy.menuachat.ui.Activity.PlatCreator;
import merejy.menuachat.ui.Activity.ToActivity;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;


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

                Intent secondeActivite = null ;
                if(need.getQuantite() == 0){
                    Toast.makeText(activity, R.string.error_zeroQuantite,Toast.LENGTH_LONG).show();
                }
                switch (target){
                    case LIST_INGREDIENT :
                        secondeActivite = new Intent(activity,ListeIngredientActivity.class);
                        if(need.getQuantite() != 0){
                            Needing.getNeeding().add(need);
                        }
                        break;
                    case PLAT_CREATOR:
                        secondeActivite = new Intent(activity,PlatCreator.class);

                        for(int i = 0 ; i<val ; i++){
                            PlatCreator.addIngredient(ingredient);
                        }
                        break;
                    default:break;
                }

                if(secondeActivite != null){
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
}
