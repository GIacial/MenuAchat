package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.ui.Button.OnClickListenerCreator.ChoiceIngredient.ChoiceIngredient_Modifier_OnclickListenerCreator;
import merejy.menuachat.ui.Button.OnClickListenerCreator.ChoicePlat.ChoicePlat_Modifier_OnClickListenerCreator;
import merejy.menuachat.ui.ViewAdapter.ChoiceInSortedIngredientListAdapter;
import merejy.menuachat.ui.ViewAdapter.ChoiceIngredientAdapter;
import merejy.menuachat.ui.ViewAdapter.ChoicePlatAdapter;

public class DatabaseModifierActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_modifier_main_layout);

        final Database database = Database.getDatabase();

        RecyclerView platView = findViewById(R.id.platList);
        platView.setLayoutManager(new LinearLayoutManager(this));
        platView.setAdapter(new ChoicePlatAdapter(database.getAllPlat(),this,new ChoicePlat_Modifier_OnClickListenerCreator()));

        final RecyclerView ingredientView = findViewById(R.id.ingredientList);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));
        ingredientView.setAdapter(new ChoiceInSortedIngredientListAdapter(database.getAllIngredient(),this,new ChoiceIngredient_Modifier_OnclickListenerCreator()));


        final CheckBox regrouperParCategorie = findViewById(R.id.regrouperParCategorie);
        regrouperParCategorie.setChecked(true);
        regrouperParCategorie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(regrouperParCategorie.isChecked()){
                    ingredientView.setAdapter(new ChoiceInSortedIngredientListAdapter(database.getAllIngredient(),DatabaseModifierActivity.this,new ChoiceIngredient_Modifier_OnclickListenerCreator()));

                }
                else{
                    ingredientView.setAdapter(new ChoiceIngredientAdapter(database.getAllIngredient(),DatabaseModifierActivity.this,new ChoiceIngredient_Modifier_OnclickListenerCreator()));

                }
            }
        });

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
