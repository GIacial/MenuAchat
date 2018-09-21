package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.R;
import merejy.menuachat.database.DataEnum.CategoriePlats;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.ui.ViewAdapter.IngredientListAdapter;

public class PlatCreator extends ActivitySaveOnClose {

    static private List<Ingredient> need = new ArrayList<>();
    static private String nom = "";
    static private int catego = 0;

    public static void  addIngredient(Ingredient i){
        need.add(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plat_creator);

        final EditText nom = findViewById(R.id.nomPlatEdit);
        final Spinner cat = findViewById(R.id.categoriePlatEdit);
        Button comfirmer = findViewById(R.id.button_comfimer_creator_Plat);
        RecyclerView ingredient = findViewById(R.id.ingredientListForPlat);
        FloatingActionButton addIngredient = findViewById(R.id.IngredientAjoutPlat);

        //nom
        nom.setText(PlatCreator.nom);

        //spinner
        cat.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, CategoriePlats.values()));
        cat.setSelection(PlatCreator.catego);

        //recycler
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ingredient.setHasFixedSize(true);

        // use a linear layout manager
        ingredient.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        ingredient.setAdapter(new IngredientListAdapter(need));
        //button
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nom.getText().toString().equals("") && need.size() > 0){
                    try {
                        Database.getDatabase().addPlat(nom.getText().toString(),(CategoriePlats) cat.getSelectedItem(),need);
                    } catch (ItemAlreadyExist itemAlreadyExist) {
                        Toast.makeText(PlatCreator.this,R.string.error_PlatExistant,Toast.LENGTH_LONG).show();
                    }
                    need = new ArrayList<>();
                    PlatCreator.nom = "";
                    PlatCreator.catego = 0;
                }
                else {
                    Toast.makeText(PlatCreator.this,R.string.error_Plat_needNameOrIngredient,Toast.LENGTH_LONG).show();
                }
                Intent secondeActivite =  new Intent(PlatCreator.this,AllPlatList.class);
                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(PlatCreator.this, All_IngredientList.class);
                All_IngredientList.target = PlatCreator.this.getActivityEnum();
                PlatCreator.nom = nom.getText().toString();
                PlatCreator.catego = cat.getSelectedItemPosition();
                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });

        }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.PLAT_CREATOR;
    }
}
