package merejy.menuachat.ui.Activity.Materiaux;

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
import merejy.menuachat.database.DataEnum.CategorieMateriaux;
import merejy.menuachat.database.Database;
import merejy.menuachat.ui.Activity.Abstract.ActivitySaveOnClose;
import merejy.menuachat.ui.Activity.ToActivity;

public class MateriauxCreator extends ActivitySaveOnClose {

    public static ToActivity nextActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materiaux_creator);

        final EditText nom = findViewById(R.id.nomEdit);
        final Spinner cat = findViewById(R.id.categorieEdit);

        cat.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, CategorieMateriaux.values()));

        Button comfirmer = findViewById(R.id.button_comfimer);

        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomText = nom.getText().toString();
                CategorieMateriaux catSelect = (CategorieMateriaux) cat.getSelectedItem();
                if(!nomText.isEmpty() && catSelect != null){
                    try {
                        Database.getDatabase().addMateriaux(nomText,catSelect);
                    } catch (ItemAlreadyExist itemAlreadyExist) {
                        Toast.makeText(MateriauxCreator.this, R.string.error_IngredientExistant, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MateriauxCreator.this, R.string.error_Name_notWrite_ingredient, Toast.LENGTH_SHORT).show();
                }
                Intent secondActivity = ToActivity.getIntentToGoTo(MateriauxCreator.this,nextActivity);
                if(secondActivity != null){
                    startActivity(secondActivity);
                }

            }
        });
    }


    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.MATERIAUX_CREATOR;
    }
}
