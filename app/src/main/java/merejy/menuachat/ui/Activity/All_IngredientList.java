package merejy.menuachat.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.ui.Button.OnClickListenerCreator.ChoiceIngredient.ChoiceIngredient_Take_OnClickListenerCreator;
import merejy.menuachat.ui.ViewAdapter.ChoiceInSortedIngredientListAdapter;
import merejy.menuachat.ui.ViewAdapter.ChoiceIngredientAdapter;

public class All_IngredientList extends AbstractActivity {

    public static ToActivity target = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_ingredient_activity);



        final Database database = Database.getDatabase();

        //recup de la liste
        final RecyclerView listUi = findViewById(R.id.ingredientListAll);

        // use this setting to improve performance if you know that changes

        // use a linear layout manager
        listUi.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        listUi.setAdapter(new ChoiceInSortedIngredientListAdapter(database.getAllIngredient(),this,new ChoiceIngredient_Take_OnClickListenerCreator()));
        //listUi.setAdapter(new ChoiceIngredientAdapter(n.getAllIngredient(),this));

        //button
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
                Intent secondeActivite = ToActivity.getIntentToGoTo(All_IngredientList.this,All_IngredientList.target) ;

                if(secondeActivite != null){

                    startActivity(secondeActivite);
                }

            }
        });

        //search bar
        final SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){//interface par defaut quand pas de text
                    listUi.setAdapter(new ChoiceInSortedIngredientListAdapter(database.getAllIngredient(),All_IngredientList.this,new ChoiceIngredient_Take_OnClickListenerCreator()));
                }
                else{
                    List<Ingredient> ingredients = new ArrayList<>();
                    for(Ingredient i : database.getAllIngredient()){
                        if(i.getNom().toLowerCase().contains(newText.toLowerCase())){
                            ingredients.add(i);
                        }
                    }
                    listUi.setAdapter(new ChoiceIngredientAdapter(ingredients,All_IngredientList.this,new ChoiceIngredient_Take_OnClickListenerCreator()));

                }
                return false;
            }
        });
       // searchBar.setSuggestionsAdapter(new SimpleCursorAdapter(this,R.layout.sugestion_layout,null,(String[])database.getAllNameOfIngredient().toArray(),null,0));


    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.ALL_INGREDIENT;
    }


}
