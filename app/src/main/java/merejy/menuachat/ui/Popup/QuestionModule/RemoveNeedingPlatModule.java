package merejy.menuachat.ui.Popup.QuestionModule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.kernel.NeedingPlat;
import merejy.menuachat.ui.ViewAdapter.IngredientAdapter;
import merejy.menuachat.ui.ViewAdapter.PlatAdapter;

public class RemoveNeedingPlatModule implements QuestionPopupModule {

    private NeedingPlat plat;
    private RecyclerView recyclerView;
    private TextView listTotalPrix;
    private Activity activity;


    public RemoveNeedingPlatModule( RecyclerView recyclerView , TextView prixTotal , NeedingPlat plat , Activity activity){
        this.plat = plat;
        this.recyclerView = recyclerView;
        this.listTotalPrix = prixTotal;
        this.activity = activity;
    }


    @Override
    public String getQuestion() {
        return activity.getResources().getString(R.string.text_questionSuppression ) +" "+ plat.getNom() + " ?";
    }

    @Override
    public Runnable getMethodOnComfirm() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Needing.getNeeding().remove(plat);
                } catch (ItemNotfound itemNotfound) {
                    Toast.makeText(recyclerView.getContext(), R.string.error_platNotFound,Toast.LENGTH_LONG).show();
                }
                recyclerView.setAdapter(new PlatAdapter(Needing.getNeeding().getPlats(),recyclerView,activity,listTotalPrix));
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
