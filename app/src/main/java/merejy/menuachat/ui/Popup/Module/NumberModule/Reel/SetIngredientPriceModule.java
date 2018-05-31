package merejy.menuachat.ui.Popup.Module.NumberModule.Reel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.ui.Popup.ReelNumberPopup;
import merejy.menuachat.ui.ViewAdapter.IngredientAdapter;

public class SetIngredientPriceModule implements ReelNumberPopupModule {

     private InterfaceNeedingIngredient ingredient = null;
     private RecyclerView listView;
     private TextView textTotal;
     private Activity activity;


     public SetIngredientPriceModule (Activity activity,InterfaceNeedingIngredient ingredient , RecyclerView listView , TextView textTotal){
         this.ingredient = ingredient;
         this.listView = listView;
         this.textTotal = textTotal;
         this.activity = activity;
     }


    @Override
    public Runnable getMethodOnComfirm(final double val) {
        return new Runnable() {
            @Override
            public void run() {
                    ingredient.addPrix(val, Needing.getNeeding().getCurrentMag());
                    listView.setAdapter(new IngredientAdapter(Needing.getNeeding().getIngredients(),listView,activity,textTotal));
                    textTotal.setText(Needing.getNeeding().getTotal()+"");

            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
