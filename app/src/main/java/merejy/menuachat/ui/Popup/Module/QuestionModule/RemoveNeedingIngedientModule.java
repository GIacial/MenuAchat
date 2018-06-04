package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.ui.ViewAdapter.NeedingIngredientAdapter;

public class RemoveNeedingIngedientModule implements QuestionPopupModule {

    private InterfaceNeedingIngredient ingredient;
    private Activity activity;
    private RecyclerView recyclerView;
    private TextView listTotalPrix;


    public RemoveNeedingIngedientModule(Activity activity , RecyclerView recyclerView , TextView prixTotal , InterfaceNeedingIngredient ingredient){
        this.ingredient = ingredient;
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.listTotalPrix = prixTotal;
    }


    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionSuppression ) +" "+ ingredient.getNom() + " ?";
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    NeedingList.getNeeding().remove(ingredient.getNom());
                } catch (ItemNotfound itemNotfound) {
                    Toast.makeText(activity, R.string.error_ingredientNotFound,Toast.LENGTH_LONG).show();
                }
                recyclerView.setAdapter(new NeedingIngredientAdapter(NeedingList.getNeeding().getIngredients(),recyclerView,activity,listTotalPrix));
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
