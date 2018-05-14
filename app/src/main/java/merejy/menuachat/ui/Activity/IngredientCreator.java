package merejy.menuachat.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import merejy.menuachat.R;
import merejy.menuachat.database.CategorieIngredient;
import merejy.menuachat.database.Database;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient;

public class IngredientCreator extends ActivitySaveOnClose {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_creator);

        final EditText nom = findViewById(R.id.nomEdit);
        final Spinner  cat = findViewById(R.id.categorieEdit);
        Button   comfirmer = findViewById(R.id.button_comfimer_creator_ingredient);
        final  EditText prix = findViewById(R.id.prixEdit);

        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nom.getText().equals("") && prix.getText().toString()!=""){
                    Database.getDatabase().addIngedient(nom.getText().toString(),(CategorieIngredient) cat.getSelectedItem());
                    Database.getDatabase().getIngredient(nom.getText().toString()).addPrix(Double.parseDouble(prix.getText().toString()),Needing.getNeeding().getCurrentMag());
                }
                Intent secondeActivite =  new Intent(IngredientCreator.this,All_IngredientList.class);

                startActivity(secondeActivite);
            }
        });

        cat.setAdapter(new ArrayAdapter<CategorieIngredient>(this,R.layout.support_simple_spinner_dropdown_item,CategorieIngredient.values()));
    }
}
