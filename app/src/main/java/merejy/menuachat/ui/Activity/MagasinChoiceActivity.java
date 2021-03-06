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

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.kernel.Needing.NeedingList;

public class MagasinChoiceActivity extends AbstractActivity {

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

        magasin.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,d.getAllMagasin().toArray()));
        magasin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lieu.setAdapter(new ArrayAdapter<>(MagasinChoiceActivity.this,R.layout.support_simple_spinner_dropdown_item,d.getAllMagasinLocation(magasin.getSelectedItem().toString()).toArray()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                lieu.setAdapter(new ArrayAdapter<>(MagasinChoiceActivity.this,R.layout.support_simple_spinner_dropdown_item,new ArrayList<>().toArray()));
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
                    NeedingList.getNeeding().setCurrentMag(d.getMagasin(magasin.getSelectedItem().toString(),lieu.getSelectedItem().toString()));
                    Intent secondeActivite =  ToActivity.getIntentToGoTo(MagasinChoiceActivity.this,ToActivity.LIST_INGREDIENT);
                    if(secondeActivite != null){
                        startActivity(secondeActivite);
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite =  ToActivity.getIntentToGoTo(MagasinChoiceActivity.this,ToActivity.MAGASIN_CREATOR);
                if(secondeActivite != null){
                    startActivity(secondeActivite);
                }
            }
        });


    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.MAGASIN_CHOICE;
    }
}
