package merejy.menuachat.ui.Activity.Ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.ui.Activity.Abstract.ActivitySaveOnClose;
import merejy.menuachat.ui.Activity.ToActivity;
import merejy.menuachat.ui.ViewAdapter.Ingredient.ChoiceIngredientAdapter;

public class All_IngredientList extends ActivitySaveOnClose {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_ingredient_activity);



        Database n = Database.getDatabase();

        //recup de la liste
        RecyclerView listUi = findViewById(R.id.ingredientListAll);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        listUi.setHasFixedSize(true);

        // use a linear layout manager
        listUi.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        listUi.setAdapter(new ChoiceIngredientAdapter(n.getAllIngredient(),this));

        //butoon
        FloatingActionButton p = findViewById(R.id.IngredientAjoutDatabase);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite =  new Intent(All_IngredientList.this,IngredientCreator.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });

        Button anuller = findViewById(R.id.buttonAnuler);
        anuller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = ToActivity.getIntentToGoTo(All_IngredientList.this,ToActivity.LIST_INGREDIENT) ;

                if(secondeActivite != null){
                    startActivity(secondeActivite);
                }

            }
        });



    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.ALL_INGREDIENT;
    }


}