package merejy.menuachat.ui.Popup.Module.QuestionModule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingPlat.NeedingPlat;
import merejy.menuachat.ui.ViewAdapter.Needing.Plat.PlatAdapter;

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
                    NeedingList.getNeeding().remove(plat);
                } catch (ItemNotfound itemNotfound) {
                    Toast.makeText(recyclerView.getContext(), R.string.error_platNotFound,Toast.LENGTH_LONG).show();
                }
                recyclerView.setAdapter(new PlatAdapter(NeedingList.getNeeding().getPlats(),recyclerView,activity,listTotalPrix));
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
