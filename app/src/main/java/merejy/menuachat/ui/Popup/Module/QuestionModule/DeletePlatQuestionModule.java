package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Plat;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Button.OnClickListenerCreator.ChoicePlat.ChoicePlat_Delete_OnClickListenerCreator;
import merejy.menuachat.ui.ViewAdapter.ChoicePlatAdapter;

public class DeletePlatQuestionModule implements QuestionPopupModule {

    private Plat plat;
    private RecyclerView liste;
    private AbstractActivity activity;


    public DeletePlatQuestionModule(Plat plat, RecyclerView liste,AbstractActivity activity){
        this.plat = plat;
        this.liste = liste;
        this.activity = activity;
    }

    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_supprimer)+" "+plat.getNom()+" ?";
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                Database.getDatabase().removePlat(plat);
                liste.setAdapter(new ChoicePlatAdapter(Database.getDatabase().getAllPlat(),activity,new ChoicePlat_Delete_OnClickListenerCreator(liste)));
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
