package merejy.menuachat.ui.Activity.Ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.R;
import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Materiaux;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.ui.Activity.Abstract.ActivitySaveOnClose;
import merejy.menuachat.ui.Activity.ToActivity;

public class IngredientCreator extends ActivitySaveOnClose {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_creator);

        final EditText nom = findViewById(R.id.nomEdit);
        nom.setText("");
        final Spinner  cat = findViewById(R.id.categorieEdit);
        Button   comfirmer = findViewById(R.id.button_comfimer_creator_ingredient);
        final  EditText prix = findViewById(R.id.prixEdit);
        prix.setText("");
        final Spinner materiaux = findViewById(R.id.materiauxSelect);
        final EditText quantite = findViewById(R.id.quantiteEdit);

        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nom.getText().toString().isEmpty()  && !quantite.getText().toString().isEmpty() && materiaux.getSelectedItem() != null) {
                    try {
                        Database.getDatabase().addIngedient(nom.getText().toString(), (CategorieIngredient) cat.getSelectedItem(), (Materiaux) materiaux.getSelectedItem(),Integer.parseInt(quantite.getText().toString()));
                        if (prix.getText().toString().length() > 0) {
                                double prixValue = Double.parseDouble(prix.getText().toString());
                                Database.getDatabase().getIngredient(nom.getText().toString()).addPrix(prixValue, NeedingList.getNeeding().getCurrentMag());

                        }
                    } catch (ItemAlreadyExist itemAlreadyExist) {
                        Toast.makeText(IngredientCreator.this,R.string.error_IngredientExistant,Toast.LENGTH_LONG).show();
                    }
                    catch (NumberFormatException ignored){

                    }
                }
                else{
                    Toast.makeText(IngredientCreator.this,R.string.error_Name_notWrite_ingredient,Toast.LENGTH_LONG).show();
                }

                Intent secondeActivite =  ToActivity.getIntentToGoTo(IngredientCreator.this,ToActivity.ALL_INGREDIENT);
                if(secondeActivite != null){
                    startActivity(secondeActivite);
                }
            }
        });

        cat.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,CategorieIngredient.values()));

        materiaux.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Database.getDatabase().getAllMateriaux().toArray()));

        FloatingActionButton ajouteMateriaux = findViewById(R.id.creeMateriaux);
        ajouteMateriaux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondAct = ToActivity.getIntentToGoTo(IngredientCreator.this,ToActivity.MATERIAUX_CREATOR);
                if(secondAct != null){
                    startActivity(secondAct);
                }
            }
        });

    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.INGREDIENT_CREATOR;
    }
}
