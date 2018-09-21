package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.R;
import merejy.menuachat.database.Database;

public class MagasinCreator extends ActivitySaveOnClose {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magasin_creator);

        final EditText nom = findViewById(R.id.nomMagasinEdit);
        final EditText lieu = findViewById(R.id.lieuMagasinEdit);
        Button comfirmer = findViewById(R.id.button_comfimer_creator_magasin);

        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nom.getText().toString().equals("") && !lieu.getText().toString().equals("")){

                    try {
                        Database.getDatabase().addMagasin(nom.getText().toString(),lieu.getText().toString());
                    } catch (ItemAlreadyExist itemAlreadyExist) {
                        Toast.makeText(MagasinCreator.this,R.string.error_MagasinExistant,Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(MagasinCreator.this,R.string.error_Magasin_needNameOrLocalisation,Toast.LENGTH_LONG).show();
                }
                Intent secondeActivite =  new Intent(MagasinCreator.this,MagasinChoiceActivity.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });

    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.MAGASIN_CREATOR;
    }

}
