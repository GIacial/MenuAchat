package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.ui.Button.OnClickListenerCreator.ChoicePlat_Modifier_OnClickListenerCreator;
import merejy.menuachat.ui.ViewAdapter.ChoicePlatAdapter;

public class DatabaseModifierActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_modifier_main_layout);

        Database database = Database.getDatabase();

        RecyclerView platView = findViewById(R.id.platList);
        platView.setLayoutManager(new LinearLayoutManager(this));
        platView.setAdapter(new ChoicePlatAdapter(database.getAllPlat(),this,new ChoicePlat_Modifier_OnClickListenerCreator()));


        Button comfirmer = findViewById(R.id.button_comfirmer);
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent secondeActivite =  ToActivity.getIntentToGoTo(DatabaseModifierActivity.this,ToActivity.LIST_INGREDIENT);
                    if(secondeActivite != null){
                        startActivity(secondeActivite);
                    }


            }
        });
    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.DATABASE_MODIFIER;
    }
}
