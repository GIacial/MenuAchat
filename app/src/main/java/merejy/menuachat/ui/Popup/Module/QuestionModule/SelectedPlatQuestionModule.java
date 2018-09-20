package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingPlat.NeedingPlat;
import merejy.menuachat.ui.Activity.ListePlatActivity;
import merejy.menuachat.ui.Activity.ToActivity;

public class SelectedPlatQuestionModule implements QuestionPopupModule {

    private Plat plat ;
    private Activity activity;


    public SelectedPlatQuestionModule(Plat plat,Activity activity){
        this.plat = plat;
        this.activity = activity;
    }

    @Override
    public String getQuestion() {
        String question = "Voulez vous ajoutez "+plat.getNom()+ " à la liste ?\n\n Composition :\n";
        List<Ingredient> ingredientList = plat.getIngredients();

        for(Ingredient i : ingredientList){
            question += i.getNom() +"\n";
        }

        return question ;
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                NeedingList.getNeeding().add(new NeedingPlat(plat));

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
}
