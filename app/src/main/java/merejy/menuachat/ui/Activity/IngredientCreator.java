package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import merejy.menuachat.kernel.Needing;

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

        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nom.getText().toString().length() > 0 ) {
                    try {
                        Database.getDatabase().addIngedient(nom.getText().toString(), (CategorieIngredient) cat.getSelectedItem());
                        if (prix.getText().toString().length() > 0) {
                                double prixValue = Double.parseDouble(prix.getText().toString());
                                Database.getDatabase().getIngredient(nom.getText().toString()).addPrix(prixValue, Needing.getNeeding().getCurrentMag());

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

                Intent secondeActivite =  new Intent(IngredientCreator.this,All_IngredientList.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });

        cat.setAdapter(new ArrayAdapter<CategorieIngredient>(this,R.layout.support_simple_spinner_dropdown_item,CategorieIngredient.values()));
    }
}
