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
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing.NeedingList;

public class IngredientCreator extends ActivitySaveOnClose {

    private static Ingredient origine = null;

    public static void setModifyMode (Ingredient origine){
        IngredientCreator.origine = origine;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_creator);

        //valeur par défault
        String nomValue = "";
        CategorieIngredient categorieIngredientValue = CategorieIngredient.ANIMAUX;

        //Recup IHM
        final  EditText prix = findViewById(R.id.prixEdit);
        final EditText nom = findViewById(R.id.nomEdit);
        final Spinner  cat = findViewById(R.id.categorieEdit);
        Button   comfirmer = findViewById(R.id.button_comfimer_creator_ingredient);

        //config Modify
        if( origine != null){
            nomValue = origine.getNom();
            categorieIngredientValue = origine.getCategorie();
            prix.setVisibility(View.GONE);
        }

        //init
        cat.setAdapter(new ArrayAdapter<CategorieIngredient>(this,R.layout.support_simple_spinner_dropdown_item,CategorieIngredient.values()));

        nom.setText(nomValue);
        cat.setSelection(categorieIngredientValue.ordinal());
        prix.setText("");


        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nom.getText().toString().length() > 0 ) {
                    try {
                        if(origine == null) {
                            Database.getDatabase().addIngedient(nom.getText().toString(), (CategorieIngredient) cat.getSelectedItem());
                        }
                        else{
                            Database.getDatabase().modifyIngredient(origine,new Ingredient(nom.getText().toString(), (CategorieIngredient) cat.getSelectedItem()));
                        }
                        if (prix.getText().toString().length() > 0 && origine == null) {
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

                Intent secondeActivite = null;
                if(origine == null){
                    secondeActivite = ToActivity.getIntentToGoTo(IngredientCreator.this,ToActivity.ALL_INGREDIENT);
                }
                else{
                    secondeActivite = ToActivity.getIntentToGoTo(IngredientCreator.this,ToActivity.DATABASE_MODIFIER);
                }
                IngredientCreator.origine = null; //reset
                if(secondeActivite != null){
                    startActivity(secondeActivite);
                }

            }
        });

    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.INGREDIENT_CREATOR;
    }
}
