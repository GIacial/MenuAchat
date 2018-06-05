package merejy.menuachat.ui.Activity.Materiaux;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.ui.Activity.Abstract.AbstractActivity;
import merejy.menuachat.ui.Activity.Plat.PlatCreator;
import merejy.menuachat.ui.Activity.ToActivity;
import merejy.menuachat.ui.ViewAdapter.Ingredient.ChoiceIngredientAdapter;
import merejy.menuachat.ui.ViewAdapter.Materiaux.MateriauxListAdapter;

public class AllMateriauxActivity extends AbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_materiaux_activity);

        RecyclerView list = findViewById(R.id.listAll);
        Button annuler = findViewById(R.id.buttonAnuler);
        FloatingActionButton addMateriaux = findViewById(R.id.creeMateriaux);

        // use a linear layout manager
        list.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        list.setAdapter(new MateriauxListAdapter(Database.getDatabase().getAllMateriaux(),this));

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity = ToActivity.getIntentToGoTo(AllMateriauxActivity.this, ToActivity.PLAT_CREATOR);
                if(secondActivity != null){
                    startActivity(secondActivity);
                }
            }
        });

        addMateriaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity = ToActivity.getIntentToGoTo(AllMateriauxActivity.this, ToActivity.MATERIAUX_CREATOR);
                if(secondActivity != null){
                    startActivity(secondActivity);
                }
            }
        });
    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.ALL_MATERIAUX;
    }
}
