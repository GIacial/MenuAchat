package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.kernel.Needing;

public class MagasinChoiceActivity extends ActivitySaveOnClose {

    private boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magasin_selector_activity);

        final Spinner magasin = findViewById(R.id.choixMagasinSpinnerMagasin);
        final Spinner lieu    =  findViewById(R.id.choixMagasinSpinnerLieu);
        FloatingActionButton add = findViewById(R.id.MagasinAdd);
        Button comfirmer = findViewById(R.id.choixMagasinComfirmer);

        final Database d = Database.getDatabase();

        magasin.setAdapter(new ArrayAdapter<Object>(this,R.layout.support_simple_spinner_dropdown_item,d.getAllMagasin().toArray()));
        magasin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lieu.setAdapter(new ArrayAdapter<Object>(MagasinChoiceActivity.this,R.layout.support_simple_spinner_dropdown_item,d.getAllMagasinLocation(magasin.getSelectedItem().toString()).toArray()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                List<String> a = new ArrayList<>();
                lieu.setAdapter(new ArrayAdapter<Object>(MagasinChoiceActivity.this,R.layout.support_simple_spinner_dropdown_item,a.toArray()));
            }
        });

        lieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ok = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ok = false;
            }
        });

        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ok){
                    Needing.getNeeding().setCurrentMag(d.getMagasin(magasin.getSelectedItem().toString(),lieu.getSelectedItem().toString()));
                    Intent secondeActivite =  new Intent(MagasinChoiceActivity.this,ListePlatActivity.class);
                    startActivity(secondeActivite);
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite =  new Intent(MagasinChoiceActivity.this,MagasinCreator.class);
                startActivity(secondeActivite);
            }
        });


    }
}