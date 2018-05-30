package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.ui.ViewAdapter.ChoicePlatAdapter;

public class AllPlatList extends ActivitySaveOnClose {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_plat_activity);

        Database d = Database.getDatabase();

        //recup de la liste
        RecyclerView listUi = findViewById(R.id.platListAll);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        listUi.setHasFixedSize(true);

        // use a linear layout manager
        listUi.setLayoutManager(new LinearLayoutManager(this));
        //mets un adapter
        listUi.setAdapter(new ChoicePlatAdapter(d.getAllPlat(), this));

        //butoon
        FloatingActionButton p = findViewById(R.id.platAjoutDatabase);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite =  new Intent(AllPlatList.this,PlatCreator.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                System.err.println("lancement activity");
                startActivity(secondeActivite);
            }
        });

        Button anuller = findViewById(R.id.buttonAnuler);
        anuller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(AllPlatList.this,ListePlatActivity.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });


    }

    @Override
    ToActivity getActivityEnum() {
        return ToActivity.ALL_PLAT;
    }
}
