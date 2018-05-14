package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                if(nom.getText().toString()!="" && lieu.getText().toString()!=""){
                    Database.getDatabase().addMagasin(nom.getText().toString(),lieu.getText().toString());
                }
                Intent secondeActivite =  new Intent(MagasinCreator.this,MagasinChoiceActivity.class);
                startActivity(secondeActivite);
            }
        });

    }

}
