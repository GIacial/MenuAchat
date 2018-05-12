package merejy.menuachat.ui.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import merejy.menuachat.R;
import merejy.menuachat.database.CategorieIngredient;
import merejy.menuachat.database.CategoriePlats;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingPlat;
import merejy.menuachat.ui.ViewAdapter.PlatAdapter;

public class ListePlatActivity extends ActivitySaveOnClose {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }

         Needing n = Needing.getNeeding();

        //ui

        RecyclerView listUi = findViewById(R.id.platList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // listUi.setHasFixedSize(true);

        // use a linear layout manager
        listUi.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        listUi.setAdapter(new PlatAdapter(n.getPlats(),listUi));

        //calcul du total
        TextView total = findViewById(R.id.prixTotal);
        total.setText(n.getTotal(null)+"");

        //mise en marche du butoon
        Button b = findViewById(R.id.buttonGenere);
        b.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(ListePlatActivity.this, ListeIngredientActivity.class);
                startActivity(secondeActivite);
            }
        });

        FloatingActionButton p = findViewById(R.id.platAjout);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(ListePlatActivity.this, AllPlatList.class);
                startActivity(secondeActivite);
            }
        });
    }
}
