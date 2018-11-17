package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import merejy.menuachat.R;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.ui.Activity.PlatCreator;
import merejy.menuachat.ui.ViewAdapter.IngredientListAdapter;

public class RemoveIngredientPlatModule implements QuestionPopupModule {

    private Ingredient ingredient;
    private Activity activity;
    private RecyclerView.Adapter adapteur;
    List<Ingredient> list;

    public RemoveIngredientPlatModule(Activity activity , RecyclerView.Adapter adapteur , Ingredient ingredient, List<Ingredient> list){
        this.ingredient = ingredient;
        this.activity = activity;
        this.adapteur = adapteur;
        this.list = list;
    }
    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionSuppression ) +" "+ ingredient.getNom() + "du plat ?";
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                PlatCreator.deleteIngredient(ingredient);
                int position = list.indexOf(ingredient);
                list.remove(ingredient);
                adapteur.notifyItemRemoved(position);
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
