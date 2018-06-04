package merejy.menuachat.ui.Popup.Module.NumberModule.Reel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.ui.ViewAdapter.NeedingIngredientAdapter;

public class SetIngredientPriceModule implements ReelNumberPopupModule {

     private InterfaceNeedingIngredient ingredient = null;
     private RecyclerView listView;
     private TextView textTotal;
     private Activity activity;
    static private NumberFormat priceFormat = new DecimalFormat("#.##");


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
                    ingredient.addPrix(val, NeedingList.getNeeding().getCurrentMag());
                    listView.setAdapter(new NeedingIngredientAdapter(NeedingList.getNeeding().getIngredients(),listView,activity,textTotal));
                    textTotal.setText(priceFormat.format(NeedingList.getNeeding().getTotal()));

            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }

    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_question_prix);
    }
}
