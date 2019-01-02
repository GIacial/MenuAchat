package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import merejy.menuachat.database.Plat;
import merejy.menuachat.ui.ViewAdapter.IngredientListAdapter;

public class PlatCreator extends ActivitySaveOnClose {

    static private List<Ingredient> need = new ArrayList<>();
    static private String nom = "";
    static private int catego = 0;
    static private boolean accompagner = true;
    static private boolean creation = true;
    static private Plat origine = null;

    public static void  addIngredient(Ingredient i){
        need.add(i);
    }
    public static void  deleteIngredient(Ingredient i) {
        need.remove(i);
    }
    public static void  setModifyMode(Plat origine){
        if(origine != null){
            creation = false;
            nom = origine.getNom();
            catego = origine.getCategories().ordinal();
            accompagner = origine.isAccompagner();
            need = origine.getIngredients();
            PlatCreator.origine = origine;
        }

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
        final CheckBox accompagnement = findViewById(R.id.checkAccompagnement);//Recup CheckBox accompagnement
        accompagnement.setChecked(accompagner);
                //nom
        nom.setText(PlatCreator.nom);

        //spinner
        cat.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, CategoriePlats.values()));
        cat.setSelection(PlatCreator.catego);


        // use a linear layout manager
        ingredient.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        ingredient.setAdapter(new IngredientListAdapter(need,this));



        //button
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite;
                if(creation){
                    secondeActivite =  ToActivity.getIntentToGoTo(PlatCreator.this,ToActivity.ALL_PLAT);
                }
                else{
                    secondeActivite =  ToActivity.getIntentToGoTo(PlatCreator.this,ToActivity.DATABASE_MODIFIER);
                }
                if(!nom.getText().toString().equals("") && need.size() > 0){
                    try {
                        if(creation){
                            Database.getDatabase().addPlat(nom.getText().toString(),(CategoriePlats) cat.getSelectedItem(),need,accompagnement.isChecked());

                        }
                        else{
                            Plat nouveau = new Plat(nom.getText().toString(),(CategoriePlats) cat.getSelectedItem(),need,accompagnement.isChecked());
                            Database.getDatabase().modifyPlat(PlatCreator.origine,nouveau);
                        }
                    } catch (ItemAlreadyExist itemAlreadyExist) {
                        Toast.makeText(PlatCreator.this,R.string.error_PlatExistant,Toast.LENGTH_LONG).show();
                    }
                    //reset des data
                    need = new ArrayList<>();
                    PlatCreator.nom = "";
                    PlatCreator.catego = 0;
                    PlatCreator.accompagner = true;
                    PlatCreator.creation = true;
                }
                else {
                    Toast.makeText(PlatCreator.this,R.string.error_Plat_needNameOrIngredient,Toast.LENGTH_LONG).show();
                }

                if(secondeActivite != null){
                    startActivity(secondeActivite);
                }
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = ToActivity.getIntentToGoTo(PlatCreator.this,ToActivity.ALL_INGREDIENT);
                All_IngredientList.target = PlatCreator.this.getActivityEnum();
                PlatCreator.nom = nom.getText().toString();
                PlatCreator.catego = cat.getSelectedItemPosition();
                PlatCreator.accompagner = accompagnement.isChecked();

                if(secondeActivite != null){
                    startActivity(secondeActivite);
                }
            }
        });

        }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.PLAT_CREATOR;
    }
}
