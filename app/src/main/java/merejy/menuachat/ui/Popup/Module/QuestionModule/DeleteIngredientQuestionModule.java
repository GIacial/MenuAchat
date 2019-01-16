package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;

import merejy.menuachat.Exception.IngredientWasUseInPlat;
import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Plat;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Button.OnClickListenerCreator.ChoiceIngredient.ChoiceIngredient_Delete_OnClickListenerCreator;
import merejy.menuachat.ui.Popup.QuestionPopup;
import merejy.menuachat.ui.ViewAdapter.ChoiceInSortedIngredientListAdapter;

public class DeleteIngredientQuestionModule implements QuestionPopupModule {

    private Ingredient ingredient;
    private RecyclerView liste;
    private AbstractActivity activity;
    private CheckBox choiceInterface;


    public DeleteIngredientQuestionModule(Ingredient ingredient, RecyclerView liste, AbstractActivity activity, CheckBox choiceInterface){
        this.ingredient = ingredient;
        this.liste = liste;
        this.activity = activity;
        this.choiceInterface = choiceInterface;
    }


    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_supprimer)+" "+ ingredient.getNom() + " ?";
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Database.getDatabase().removeIngredient(ingredient);
                    liste.setAdapter(new ChoiceInSortedIngredientListAdapter(Database.getDatabase().getAllIngredient(),activity,new ChoiceIngredient_Delete_OnClickListenerCreator(liste,choiceInterface)));
                    choiceInterface.setChecked(false);
                } catch (IngredientWasUseInPlat ingredientWasUseInPlat) {
                    String text = ingredient.getNom() +" "+ activity.getResources().getString(R.string.error_SupprimerIngredient);
                    for(String nom : ingredientWasUseInPlat.getPlatsName()){
                        text += nom + "\n";
                    }
                    QuestionPopup.showDialog(activity,new PrintTextQuestionModule(text));
                }

            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
